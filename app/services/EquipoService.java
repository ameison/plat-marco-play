package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.EquipoDTO;
import entities.SeguimientoDTO;
import models.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import play.Logger;
import play.Play;
import util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class EquipoService {

    public static Map<String, Object> registrarEquipo(EquipoDTO equipoDTO){
        Map<String, Object> map = new HashMap<>();
        if (Equipo.equipoExiste(equipoDTO)){
            map.put("respuesta", "equipo-existente");
        }else{
            Equipo equipoDB = new Equipo();
            equipoDB.setNombre(equipoDTO.nombre);
            equipoDB.setEstado(Equipo.ACTIVO);
            equipoDB.setModelo(new Modelo(equipoDTO.modelo.id));
            equipoDB.setSuperintendencia(new Superintendencia(equipoDTO.superintendencia.id));
            equipoDB.setDiasOptimo(equipoDTO.diasOptimo);
            equipoDB.setDiasCritico(equipoDTO.diasCritico);
            equipoDB.save();
            map.put("respuesta", "registro-exitoso");
            Logger.debug("Se grabo el equipo correctamente");
        }
        return map;
    }

    public static Map<String, Object> actualizarEquipo(EquipoDTO equipoDTO){
        Map<String, Object> map = new HashMap<>();
        Equipo equipoDB = Equipo.find.byId(equipoDTO.id);
        if(equipoDB != null){
            if (equipoDTO.nombre != null)
                equipoDB.setNombre(equipoDTO.nombre);
            if (equipoDTO.estado != null)
                equipoDB.setEstado(equipoDTO.estado);
            if (equipoDTO.modelo != null && equipoDTO.modelo.id != null)
                equipoDB.setModelo(new Modelo(equipoDTO.modelo.id));
            if (equipoDTO.superintendencia != null && equipoDTO.superintendencia.id != null)
                equipoDB.setSuperintendencia(new Superintendencia(equipoDTO.superintendencia.id));
            if (equipoDTO.diasOptimo != null)
                equipoDB.setDiasOptimo(equipoDTO.diasOptimo);
            if (equipoDTO.diasCritico != null)
                equipoDB.setDiasCritico(equipoDTO.diasCritico);
            equipoDB.update();
            map.put("respuesta", "actualizacion-exitosa");
        }else{
            map.put("respuesta", "equipo-existe");
        }

        return map;
    }

    public static Map<String, Object> eliminarEquipo(Long id){
        Map<String, Object> map = new HashMap<>();
        Equipo equipo = Equipo.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(equipo != null){
            if (!Inspeccion.equipoExiste(equipo.getId())){
                equipo.setEliminado(true);
                equipo.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "equipo-en-uso");
            }
        }else{
            map.put("respuesta", "equipo-no-existe");
        }
        return map;
    }

    public static List<EquipoDTO> getEquipos(){
        List<Equipo> equipos = Equipo.getEquipos();
        return parseEquipoDTOList(equipos);
    }

    public static List<EquipoDTO> getEquiposXSuperintendenciaId(Long id){
        List<Equipo> equipos = Equipo.getEquiposXSuperintendenciaId(id);
        return parseEquipoDTOList(equipos);
    }

    public static List<EquipoDTO> getEquiposxMinaId(Long id){
        List<Equipo> equipos = Equipo.getEquiposXMinaId(id);
        return parseEquipoDTOList(equipos);
    }

    private static List<EquipoDTO> parseEquipoDTOList(List<Equipo> equipos){
        List<EquipoDTO> equipoDTOs = new ArrayList<>();
        equipos.stream().forEach(equipo -> equipoDTOs.add(new EquipoDTO(equipo)));
        return equipoDTOs;
    }

    public static List<Map<String, Object>> getMonitoreoTemperaturaEquipos(SeguimientoDTO sDto){
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Equipo> equipos = Equipo.getEquipoXCategoriaYMina(sDto);
        equipos.stream().forEach(equipo -> {
            Inspeccion inspeccion = Inspeccion.getUltimaInspeccionEquipoYTipo(equipo.getId(), Inspeccion.MONITOREO_TEMPERATURA);
            if (inspeccion != null){
                Map<String, Object> map = new HashMap<>();
                map.put("equipo_id", equipo.getId());
                map.put("equipo", equipo.getNombre());
                LocalDate hoy = new LocalDate(new Date());


                //HistorialInspeccion historia = HistorialInspeccion.getHistorialTipoAprobado(inspeccion.getId());
                //int dias = 0;
                //if (historia != null){

                //dias = Days.daysBetween(new LocalDate(historia.getFechaCreacion()), hoy).getDays();
                int dias = Days.daysBetween(new LocalDate(inspeccion.getFechaCreacion()), hoy).getDays();
                map.put("ultima_intervencion", Util.parseDateTime(inspeccion.getFechaCreacion()));

                map.put("dias", dias);
                map.put("estado", getEstadoSeguimiento(equipo, dias));
                maps.add(map);

                //}


            }else{
                Logger.debug("No se realizo monitoreo de temperatura en el equipo" + equipo.getNombre());
            }
        });
        return maps;
    }

    public static List<Map<String, Object>> getSeguimientoEquipos(SeguimientoDTO sDto){
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Equipo> equipos = Equipo.getEquipoXCategoriaYMina(sDto);
        equipos.stream().forEach(equipo -> {
            Inspeccion inspeccion = Inspeccion.getUltimaInspeccionEquipoYTipo(equipo.getId(), sDto.tipoSeguimiento);

            if (inspeccion != null){
                //int dias = 0;
                Map<String, Object> map = new HashMap<>();
                map.put("equipo_id", equipo.getId());
                map.put("equipo", equipo.getNombre());
                map.put("resumen", inspeccion.getResumenActividad());
                map.put("inspeccion_id", inspeccion.getId());

                LocalDate hoy = new LocalDate(new Date());
                //HistorialInspeccion historia = HistorialInspeccion.getHistorialTipoAprobado(inspeccion.getId());
                //if (historia != null){
                    //dias = Days.daysBetween(new LocalDate(historia.getFechaCreacion()), hoy).getDays();
                    int dias = Days.daysBetween(new LocalDate(inspeccion.getFechaCreacion()), hoy).getDays();
                    map.put("ultima_intervencion", Util.parseDateTime(inspeccion.getFechaCreacion()));
                //}else{
                    //map.put("ultima_intervencion", "En revisión");
                //}

                map.put("dias", dias);
                map.put("estado", getEstadoSeguimiento(equipo, dias));
                map.put("tipo_inspeccion", inspeccion.getTipo());
                maps.add(map);

                Logger.error("MAPA DE SEGUIMIENTO : " + map.toString());

            }else{
                Logger.debug("No se realizo este tipo de inspeccion en este equipo");
            }
        });
        return maps;
    }

    private static String getEstadoSeguimiento(Equipo equipo, int dias){
        int diaInicial = 0;
        int diasOptimos = equipo.getDiasOptimo();
        int diasCritico = equipo.getDiasCritico();
        if (diaInicial <= dias && dias <= diasOptimos){
            return "optimo";
//        }else if (diasOptimos > dias && dias <= diasCritico){
        }else if (diasOptimos < dias && dias <= diasCritico){
            return "normal";
        }else if (dias > diasCritico){
            return "critico";
        }
        return "optimo";
    }

    public static File generarExcelMonitoreoTemperatura(Long equipoId){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String filename = Play.application().configuration().getString("rootFolderFile") + "seguimiento_temperatura.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");
            HSSFCellStyle styleHead = workbook.createCellStyle();
            HSSFPalette palette = workbook.getCustomPalette();
            HSSFColor myColor = palette.findSimilarColor(219, 229, 241);
            short palIndex = myColor.getIndex();
            styleHead.setFillForegroundColor(palIndex);
            styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            styleHead.setFont(font);
            List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxEquipoYTipo(equipoId, Inspeccion.MONITOREO_TEMPERATURA);
            //GENERACION DE CABECERAS
            for (Inspeccion inspeccion: inspecciones){
                HistorialInspeccion hi = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());
                List<Map<String, Object>> secciones = mapper.convertValue(hi.getContenido().get("data"), ArrayList.class);
                for (Map<String, Object> seccion: secciones){
                    if (seccion.containsKey("id")){
                        Long seccionId = Long.parseLong(seccion.get("id").toString());
                        Seccion secDb = Seccion.find.byId(seccionId);
                        //CREACION DE CABECERA REPORTE
                        if (secDb != null){
                            if (secDb.isReporteTemperatura()){
                                Map<String, Object> dataSeccion = mapper.convertValue(seccion.get("contenido"), Map.class);
                                Map<String, Object> tabla = mapper.convertValue(dataSeccion.get("data"), Map.class);
                                List<Map<String, Object>> filas = mapper.convertValue(tabla.get("content"), ArrayList.class);
                                int indiceFila = 0;
                                HSSFRow rowhead = sheet.createRow(0);
                                HSSFCell cabFecha = rowhead.createCell(0);
                                cabFecha.setCellValue("FECHA DE INSPECCIÓN");
                                cabFecha.setCellStyle(styleHead);
                                int indiceColExcel = 1;
                                for (Map<String, Object> fila: filas) {
                                    if (indiceFila > 0){
                                        List<Map<String, Object>> celdas = mapper.convertValue(fila.get("content"), ArrayList.class);
                                        int indiceCelda = 0;
                                        String prefijoItem = "";
                                        for (Map<String, Object> celda: celdas){
                                            Map<String, Object> objeto = mapper.convertValue(celda.get("content"), Map.class);
                                            if (indiceCelda == 0){
                                                prefijoItem = objeto.get("value").toString();
                                            }
                                            if (indiceCelda > 1){
                                                if (objeto.get("obj").toString().equals("input-textnum") || objeto.get("obj").toString().equals("input-text") || objeto.get("obj").toString().equals("input-num")){
                                                    if (indiceCelda == 2){
                                                        HSSFCell cellBodyDin = rowhead.createCell(indiceColExcel);
                                                        cellBodyDin.setCellValue(prefijoItem+"-LH");
                                                        cellBodyDin.setCellStyle(styleHead);
                                                        indiceColExcel++;
                                                    }else if (indiceCelda == 3){
                                                        HSSFCell cellBodyDin = rowhead.createCell(indiceColExcel);
                                                        cellBodyDin.setCellValue(prefijoItem+"-RH");
                                                        cellBodyDin.setCellStyle(styleHead);
                                                        indiceColExcel++;
                                                    }
                                                }
                                            }
                                            indiceCelda++;
                                        }
                                    }
                                    indiceFila++;
                                }
                                break;
                            }
                        }
                    }else{
                        Logger.info("Esta seccion es temporal");
                    }
                }
            }

            //FIN DE GENERACION DE CABECERA
            int indiceInspeccion = 0;
            for (Inspeccion inspeccion: inspecciones){
                HistorialInspeccion hi = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());
                List<Map<String, Object>> secciones = mapper.convertValue(hi.getContenido().get("data"), ArrayList.class);
                for (Map<String, Object> seccion: secciones){
                    if (seccion.containsKey("id")){
                        Long seccionId = Long.parseLong(seccion.get("id").toString());
                        Seccion secDb = Seccion.find.byId(seccionId);
                        if (secDb != null){
                            if (secDb.isReporteTemperatura()){
                                Map<String, Object> dataSeccion = mapper.convertValue(seccion.get("contenido"), Map.class);
                                Map<String, Object> tabla = mapper.convertValue(dataSeccion.get("data"), Map.class);
                                List<Map<String, Object>> filas = mapper.convertValue(tabla.get("content"), ArrayList.class);
                                int indiceFila = 0;
                                HSSFRow rowBody = sheet.createRow(indiceInspeccion+1);
                                HSSFCell cellFecha = rowBody.createCell(0);
                                cellFecha.setCellValue(Util.parseDate(hi.getFechaCreacion()));
                                int indiceColExcel = 1;
                                String valor = "";
                                for (Map<String, Object> fila: filas) {
                                    if (indiceFila > 0){
                                        List<Map<String, Object>> celdas = mapper.convertValue(fila.get("content"), ArrayList.class);
                                        int indiceCelda = 0;
                                        for (Map<String, Object> celda: celdas){
                                            Map<String, Object> objeto = mapper.convertValue(celda.get("content"), Map.class);
                                            if (indiceCelda > 1){
                                                if (objeto.get("obj").toString().equals("input-textnum") || objeto.get("obj").toString().equals("input-text") || objeto.get("obj").toString().equals("input-num")){
                                                    if (indiceCelda == 2 || indiceCelda == 3){
                                                        HSSFCell cellBodyDin = rowBody.createCell(indiceColExcel);
                                                        String value = "";
                                                        if (objeto.containsKey("value")){
                                                            value = objeto.get("value").toString();
                                                        }
                                                        cellBodyDin.setCellValue(value);
                                                        indiceColExcel++;
                                                    }
                                                }
                                            }
                                            indiceCelda++;
                                        }
                                    }
                                    indiceFila++;
                                }
                                Logger.info(valor);
                                break;
                            }
                        }
                    }else{
                        Logger.info("Es una seccion temporal");
                    }
                }
                indiceInspeccion++;
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();

            Logger.info("Archivo retornado correctamente");

            return new File(filename);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static File generarExcelHistorialCorrectivo(){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String filename = Play.application().configuration().getString("rootFolderFile") +  "historial_correctivo.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");
            HSSFCellStyle styleHead = workbook.createCellStyle();
            HSSFPalette palette = workbook.getCustomPalette();
            HSSFColor myColor = palette.findSimilarColor(219, 229, 241);
            short palIndex = myColor.getIndex();
            styleHead.setFillForegroundColor(palIndex);
            styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            styleHead.setFont(font);

            List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxTipo(Inspeccion.MANTENIMIENTO_CORRECTIVO);

            //GENERACION DE CABECERAS
            for (Inspeccion inspeccion: inspecciones){

                HistorialInspeccion hi = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());

                List<Map<String, Object>> secciones = mapper.convertValue(hi.getContenido().get("data"), ArrayList.class);

                Map<String, Object> dataSeccion = mapper.convertValue(secciones.get(0).get("contenido"), Map.class);
                Map<String, Object> tabla = mapper.convertValue(dataSeccion.get("data"), Map.class);
                List<Map<String, Object>> filas = mapper.convertValue(tabla.get("content"), ArrayList.class);

                HSSFRow rowhead = sheet.createRow(0);

                HSSFCell cabInspeccion = rowhead.createCell(0);
                cabInspeccion.setCellValue("CODIGO DE INSPECCIÓN");
                cabInspeccion.setCellStyle(styleHead);

                HSSFCell cabCategoria = rowhead.createCell(1);
                cabCategoria.setCellValue("CATEGORÍA");
                cabCategoria.setCellStyle(styleHead);

                HSSFCell cabModelo = rowhead.createCell(2);
                cabModelo.setCellValue("MODELO");
                cabModelo.setCellStyle(styleHead);

                HSSFCell cabEquipo = rowhead.createCell(3);
                cabEquipo.setCellValue("EQUIPO");
                cabEquipo.setCellStyle(styleHead);

                HSSFCell cabFechaInspeccion = rowhead.createCell(4);
                cabFechaInspeccion.setCellValue("FECHA INSPECCIÓN");
                cabFechaInspeccion.setCellStyle(styleHead);

                HSSFCell cabEstado= rowhead.createCell(5);//ESTADO Q DIFERENCIA ENTRE UNA INSPECCION DE APERTURA O CIERRE
                cabEstado.setCellValue("ESTADO");
                cabEstado.setCellStyle(styleHead);

                HSSFCell cabInspeccionCerrada= rowhead.createCell(6);//ID DE INSPECCION QUE ES CERRADA POR ESTA
                cabInspeccionCerrada.setCellValue("CIERRA INSPECCION");
                cabInspeccionCerrada.setCellStyle(styleHead);

                int indiceColExcel = 7;

                List<Map<String, Object>> celdas = mapper.convertValue(filas.get(0).get("content"), ArrayList.class);

                int indiceCelda = 0;

                for (Map<String, Object> celda: celdas){
                    if (indiceCelda > 0){
                        Map<String, Object> objeto = mapper.convertValue(celda.get("content"), Map.class);
                        HSSFCell cellBodyDin = rowhead.createCell(indiceColExcel);
                        cellBodyDin.setCellValue(objeto.get("value").toString().toUpperCase());
                        cellBodyDin.setCellStyle(styleHead);
                        indiceColExcel++;
                    }else{
                        indiceCelda++;
                    }
                }
                break;
            }

            //GENERACION DEL CONTENIDO
            int indiceCuerpo = 1;

            for (Inspeccion inspeccion: inspecciones){

                Logger.debug("INSPECCION : " + "SIG"+inspeccion.getId());

                HistorialInspeccion hi = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());
                List<Map<String, Object>> secciones = mapper.convertValue(hi.getContenido().get("data"), ArrayList.class);

                Map<String, Object> dataSeccion = mapper.convertValue(secciones.get(0).get("contenido"), Map.class);
                Map<String, Object> tabla = mapper.convertValue(dataSeccion.get("data"), Map.class);
                List<Map<String, Object>> filas = mapper.convertValue(tabla.get("content"), ArrayList.class);

                int indiceFila = 0;

                for (Map<String, Object> fila: filas) {

                    boolean considerarFila = true;

                    if (indiceFila > 0){

                        HSSFRow rowBody = sheet.createRow(indiceCuerpo);

                        int indiceColExcel = 7;

                        List<Map<String, Object>> celdas = mapper.convertValue(fila.get("content"), ArrayList.class);
                        int indiceCelda = 0;
                        for (Map<String, Object> celda: celdas){
                            Map<String, Object> objeto = mapper.convertValue(celda.get("content"), Map.class);
                            if (indiceCelda > 0){
                                if (objeto.get("obj").toString().equals("input-textnum") ||
                                        objeto.get("obj").toString().equals("input-text") ||
                                        objeto.get("obj").toString().equals("input-num") ||
                                        objeto.get("obj").toString().equals("input-option")){

                                    if (indiceCelda == 1) {//SE CONSIDERA QUE EL RESUMEN TENGA INFO
                                      if (objeto.containsKey("value")){
                                          if (objeto.get("value").toString().trim().equals("")){
                                              considerarFila = false;
                                              break;
                                          }
                                      } else {
                                          break;
                                      }
                                    }

                                    HSSFCell cellBodyDin = rowBody.createCell(indiceColExcel);

                                    if (objeto.containsKey("value")){
                                        cellBodyDin.setCellValue(objeto.get("value").toString());
                                    }else{
                                        cellBodyDin.setCellValue("");
                                    }

                                    indiceColExcel++;

                                }
                            }
                            indiceCelda++;
                        }

                        if (considerarFila){
                            HSSFCell cellInspeccion = rowBody.createCell(0);
                            cellInspeccion.setCellValue("SIG"+inspeccion.getId());

                            HSSFCell cellCategoria = rowBody.createCell(1);
                            cellCategoria.setCellValue(inspeccion.getEquipo().getModelo().getCategoria().getNombre());

                            HSSFCell cellModelo = rowBody.createCell(2);
                            cellModelo.setCellValue(inspeccion.getEquipo().getModelo().getNombre());

                            HSSFCell cellEquipo = rowBody.createCell(3);
                            cellEquipo.setCellValue(inspeccion.getEquipo().getNombre());

                            HSSFCell cellFechaInspeccion = rowBody.createCell(4);
                            cellFechaInspeccion.setCellValue(Util.parseDate(inspeccion.getFechaCreacion()));

                            HSSFCell cellEstado = rowBody.createCell(5);
                            cellEstado.setCellValue(inspeccion.getInspeccionCerrada() != null ? "CIERRE" : "APERTURA");

                            HSSFCell cellInspeccionCerrada = rowBody.createCell(6);
                            cellInspeccionCerrada.setCellValue(inspeccion.getInspeccionCerrada() != null ? "SIG"+inspeccion.getInspeccionCerrada().getId() : "");

                            indiceCuerpo++;
                        }

                    }

                    indiceFila++;
                }

            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();

            Logger.info("Archivo retornado correctamente");

            return new File(filename);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static EquipoDTO getEquipo(Long equipoId){
        return new EquipoDTO(Equipo.find.byId(equipoId));
    }

    public static File generarExcelSeguimientoPreventivo(SeguimientoDTO sDto){
        try {
            String filename = Play.application().configuration().getString("rootFolderFile") + "seguimiento_preventivo.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFCellStyle styleHead = workbook.createCellStyle();

            HSSFPalette palette = workbook.getCustomPalette();
            HSSFColor myColor = palette.findSimilarColor(219, 229, 241);
            short palIndex = myColor.getIndex();

            styleHead.setFillForegroundColor(palIndex);
            styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFFont font = workbook.createFont();
            //font.setColor(HSSFColor.RED.index);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            styleHead.setFont(font);

            //-----------------------------
            HSSFCellStyle styleBack = workbook.createCellStyle();

            HSSFPalette pBack = workbook.getCustomPalette();
            HSSFColor cBack = pBack.findSimilarColor(219, 229, 241);
            short indexBack = cBack.getIndex();

            styleBack.setFillForegroundColor(indexBack);
            styleBack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            //-----------------------------

            //-----------------------------
            HSSFCellStyle styleCritico = workbook.createCellStyle();

            HSSFPalette pCrit = workbook.getCustomPalette();
            HSSFColor cCrit = pCrit.findSimilarColor(255, 0, 0);
            short indexCrit = cCrit.getIndex();

            styleCritico.setFillForegroundColor(indexCrit);
            styleCritico.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            //------------------------------

            //-----------------------------
            HSSFCellStyle stylePrec = workbook.createCellStyle();

            HSSFPalette pPrec = workbook.getCustomPalette();
            HSSFColor cPrec = pPrec.findSimilarColor(255, 255, 0);
            short indexPrec = cPrec.getIndex();

            stylePrec.setFillForegroundColor(indexPrec);
            stylePrec.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            //------------------------------

            //-----------------------------
            HSSFCellStyle styleOpt = workbook.createCellStyle();

            HSSFPalette pOpt = workbook.getCustomPalette();
            HSSFColor cOpt = pOpt.findSimilarColor(146, 208, 80);
            short indexOpt = cOpt.getIndex();

            styleOpt.setFillForegroundColor(indexOpt);
            styleOpt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            //------------------------------

            HSSFRow rowhead = sheet.createRow(0);

            HSSFCell cabEquipo = rowhead.createCell(0);
            HSSFCell cabUInt = rowhead.createCell(1);
            HSSFCell cabHoy = rowhead.createCell(2);
            HSSFCell cabDias = rowhead.createCell(3);
            HSSFCell cabEstado = rowhead.createCell(4);
            HSSFCell cabProx = rowhead.createCell(5);
            HSSFCell cabComen = rowhead.createCell(6);

            cabEquipo.setCellValue("EQUIPO");
            cabUInt.setCellValue("ÚLTIMA INTERVENCIÓN");
            cabHoy.setCellValue("HOY");
            cabDias.setCellValue("DIAS");
            cabEstado.setCellValue("ESTADO");
            cabProx.setCellValue("RESUMEN DE ACTIVIDAD");
            cabComen.setCellValue("COMENTARIO");

            cabEquipo.setCellStyle(styleHead);
            cabUInt.setCellStyle(styleHead);
            cabHoy.setCellStyle(styleHead);
            cabDias.setCellStyle(styleHead);
            cabEstado.setCellStyle(styleHead);
            cabProx.setCellStyle(styleHead);
            cabComen.setCellStyle(styleHead);

            List<Map<String, Object>> maps = getSeguimientoEquipos(sDto);

            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> map = maps.get(i);
                HSSFRow row = sheet.createRow(i+1);
                HSSFCell itemEquipo = row.createCell(0);
                HSSFCell itemHoy = row.createCell(2);
                itemEquipo.setCellValue(map.get("equipo").toString());
                itemHoy.setCellValue(Util.parseDate(new Date()));
                itemEquipo.setCellStyle(styleBack);
                if (map.containsKey("ultima_intervencion")){
                    row.createCell(1).setCellValue(map.get("ultima_intervencion").toString());
                }else{
                    row.createCell(1).setCellValue("En proceso de inspección");
                }
                itemHoy.setCellStyle(styleBack);
                if (map.containsKey("estado")){
                    HSSFCell itemDias = row.createCell(3);
                    HSSFCell itemEstado = row.createCell(4);
                    itemDias.setCellValue(map.get("dias").toString());
                    itemEstado.setCellValue(map.get("estado").toString().toUpperCase());
                    if (map.get("estado").toString().equals("optimo")){
                        itemEstado.setCellStyle(styleOpt);
                    }else if(map.get("estado").toString().equals("normal")){
                        itemEstado.setCellStyle(stylePrec);
                    }else if(map.get("estado").toString().equals("critico")){
                        itemEstado.setCellStyle(styleCritico);
                    }
                }
                row.createCell(5).setCellValue(map.get("resumen").toString());
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            return new File(filename);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Object>> getMantenimientoCorrectivoEquipos(SeguimientoDTO sDto){

        List<Map<String, Object>> maps = new ArrayList<>();
        List<Equipo> equipos = Equipo.getEquipoXCategoriaYMina(sDto);

        equipos.stream().forEach(equipo -> {

            List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxEquipoYTipo(equipo.getId(), Inspeccion.MANTENIMIENTO_CORRECTIVO);

            inspecciones.stream().forEach(inspeccion -> {
                Map<String, Object> map = new HashMap<>();
                map.put("equipo_id", equipo.getId());
                map.put("equipo", equipo.getNombre());
                map.put("inspeccion_id", inspeccion.getId());
                map.put("fecha_apertura", Util.parseDateTime(inspeccion.getFechaCreacion()));
                map.put("resumen_actividad",  !inspeccion.getResumenActividad().equals("") ? inspeccion.getResumenActividad() : "No especificado");
                Inspeccion inspeccionCierre = Inspeccion.getInspeccionCierre(inspeccion.getId());
                if (inspeccionCierre != null){
                    int dias = Days.daysBetween(new LocalDate(inspeccion.getFechaCreacion()), new LocalDate(inspeccionCierre.getFechaCreacion())).getDays();
                    map.put("dias", dias);
                    map.put("fecha_cierre", Util.parseDateTime(inspeccionCierre.getFechaCreacion()));
                } else {
                    map.put("fecha_cierre", "Inspección no cerrada");
                    map.put("dias", 0);
                }
                maps.add(map);
            });

        });
        return maps;
    }

    public static File generarExcelMantenimientoCorrectivo(SeguimientoDTO sDto){
        try {
            String filename = Play.application().configuration().getString("rootFolderFile") + "mantenimiento_correctivo.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFCellStyle styleHead = workbook.createCellStyle();

            HSSFPalette palette = workbook.getCustomPalette();
            HSSFColor myColor = palette.findSimilarColor(219, 229, 241);
            short palIndex = myColor.getIndex();

            styleHead.setFillForegroundColor(palIndex);
            styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFFont font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            styleHead.setFont(font);

            HSSFCellStyle styleOpt = workbook.createCellStyle();

            HSSFPalette pOpt = workbook.getCustomPalette();
            HSSFColor cOpt = pOpt.findSimilarColor(146, 208, 80);
            short indexOpt = cOpt.getIndex();

            styleOpt.setFillForegroundColor(indexOpt);
            styleOpt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow rowhead = sheet.createRow(0);

            HSSFCell cabEquipo = rowhead.createCell(0);
            HSSFCell cabUInt = rowhead.createCell(1);
            HSSFCell cabReAct = rowhead.createCell(2);
            HSSFCell cabCInt = rowhead.createCell(3);
            HSSFCell cabHoy = rowhead.createCell(4);
            HSSFCell cabDias = rowhead.createCell(5);
            HSSFCell cabComen = rowhead.createCell(6);

            cabEquipo.setCellValue("EQUIPO");
            cabUInt.setCellValue("INICIO INSPECCIÓN");
            cabReAct.setCellValue("RESUMEN DE ACTIVIDAD");
            cabCInt.setCellValue("CIERRE INSPECCIÓN");
            cabHoy.setCellValue("HOY");
            cabDias.setCellValue("DIAS");
            cabComen.setCellValue("COMENTARIO");

            cabEquipo.setCellStyle(styleHead);
            cabUInt.setCellStyle(styleHead);
            cabReAct.setCellStyle(styleHead);
            cabCInt.setCellStyle(styleHead);
            cabHoy.setCellStyle(styleHead);
            cabDias.setCellStyle(styleHead);
            cabComen.setCellStyle(styleHead);

            List<Map<String, Object>> maps = getMantenimientoCorrectivoEquipos(sDto);

            for (int i = 0; i < maps.size(); i++) {

                Logger.debug("DATOS DE LA INSPECCION" + maps.get(i));

                HSSFRow row = sheet.createRow(i+1);
                HSSFCell itemEquipo = row.createCell(0);
                HSSFCell itemHoy = row.createCell(4);

                itemEquipo.setCellValue(maps.get(i).get("equipo").toString());
                itemHoy.setCellValue(Util.parseDate(new Date()));

                row.createCell(1).setCellValue(maps.get(i).get("fecha_apertura").toString());
                row.createCell(2).setCellValue(maps.get(i).get("resumen_actividad").toString());
                row.createCell(3).setCellValue(maps.get(i).get("fecha_cierre").toString());

                HSSFCell itemDias = row.createCell(5);
                itemDias.setCellValue(maps.get(i).get("dias").toString());
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();

            Logger.info("Archivo retornado correctamente");

            return new File(filename);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> getSclDiasUltimaInspeccion(SeguimientoDTO sDto){
        Map<String, Object> map = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Integer> dias = new ArrayList<>();
        List<Equipo> equipos = Equipo.getEquipoXCategoriaYMina(sDto);
        equipos.stream().forEach(equipo -> {
            labels.add(equipo.getNombre());
            Inspeccion inspeccion = Inspeccion.getUltimaInspeccionEquipoYTipo(equipo.getId(), Inspeccion.INSPECCION_SCL);
            if (inspeccion != null){
                LocalDate hoy = new LocalDate(new Date());
                dias.add(Days.daysBetween(new LocalDate(inspeccion.getFechaCreacion()), hoy).getDays());
            }else{
                dias.add(0);
            }
        });
        map.put("labels", labels);
        map.put("dias", dias);
        return map;
    }


}
