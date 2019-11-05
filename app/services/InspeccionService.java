package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import entities.*;
import models.*;
import play.Logger;
import play.Play;
import util.Util;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class InspeccionService {

    public static Map<String, Object> registrarInspeccion(InspeccionDTO inspeccionDTO){
        Map<String, Object> map = new HashMap<>();
        Inspeccion inspeccionDb = new Inspeccion();
        inspeccionDb.setEstado(Inspeccion.PROCESO);
        inspeccionDb.setTipo(inspeccionDTO.tipo);
        inspeccionDb.setEquipo(new Equipo(inspeccionDTO.equipo.id));
        inspeccionDb.setOrdenTrabajo(inspeccionDTO.ordenTrabajo);
        inspeccionDb.setPrioridad(inspeccionDTO.prioridad);
        inspeccionDb.setResponsable(new Usuario(inspeccionDTO.responsable.id));
        inspeccionDb.setResumenActividad(inspeccionDTO.resumenActividad);
        inspeccionDb.setCompartido(inspeccionDTO.compartido);
        inspeccionDb.setHorometro(inspeccionDTO.horometro);
        inspeccionDb.setFur(new Fur(inspeccionDTO.furId));
        if (inspeccionDTO.tipo.equals(Inspeccion.MANTENIMIENTO_CORRECTIVO) && inspeccionDTO.inspeccionCerrada != null && inspeccionDTO.inspeccionCerrada.id != null){
            Inspeccion inspeccionCerrada = Inspeccion.find.byId(inspeccionDTO.inspeccionCerrada.id);
            if (inspeccionCerrada != null){
                inspeccionCerrada.setEstado(Inspeccion.FINALIZADA);
                inspeccionCerrada.update();
                inspeccionDb.setInspeccionCerrada(inspeccionCerrada);
                inspeccionDb.setEstado(Inspeccion.FINALIZADA);
                map.put("inspeccion_cerrada_id", inspeccionCerrada.getId());
            }else{
                Logger.debug("No se encontro inspeccion de cierre");
            }
        }else{
            Logger.debug("No es una inspeccion de cierre correctivo");
        }
        inspeccionDb.save();
        map.put("servidor_id", inspeccionDb.getId());
        if (inspeccionDb.getFechaCreacion() != null)
            map.put("fecha_creacion", Util.parseDate(inspeccionDb.getFechaCreacion()));
        if (inspeccionDTO.hi != null && inspeccionDTO.hi.size() > 0){
            HistorialInspeccionDTO hiDTO = inspeccionDTO.hi.get(0);
            HistorialInspeccion hiDb = new HistorialInspeccion();
            hiDb.setInspeccion(inspeccionDb);
            hiDb.setContenido(hiDTO.contenido);
            hiDb.setEstado(HistorialInspeccion.INSPECCION);
            hiDb.setUsuario(new Usuario(inspeccionDTO.responsable.id));
            hiDb.save();
        }
        Logger.debug("Se grabo la inspeccion correctamente");
        return map;
    }

    public static Map<String, Object> actualizarInspeccion(InspeccionDTO inspeccionDTO, String tipoUsuario){
        Map<String, Object> map = new HashMap<>();
        Inspeccion inspeccionDb = Inspeccion.find.byId(inspeccionDTO.id);
        if(inspeccionDb != null){
            inspeccionDb.setCompartido(inspeccionDTO.compartido);
            inspeccionDb.setAprobadoSoporte(inspeccionDTO.aprobadoSoporte);
//            if (inspeccionDTO.equipo != null)
//                if (inspeccionDTO.equipo.id != null)
//                    inspeccionDb.setEquipo(new Equipo(inspeccionDTO.equipo.id));
            if (inspeccionDTO.observaciones != null)
                inspeccionDb.setObservaciones(inspeccionDTO.observaciones);
            if (inspeccionDTO.resumenActividad != null)
                inspeccionDb.setResumenActividad(inspeccionDTO.resumenActividad);
            if (inspeccionDTO.horometro != null)
                inspeccionDb.setHorometro(inspeccionDTO.horometro);
            if (inspeccionDTO.ordenTrabajo != null)
                inspeccionDb.setOrdenTrabajo(inspeccionDTO.ordenTrabajo);
            if (inspeccionDTO.prioridad != null)
                inspeccionDb.setPrioridad(inspeccionDTO.prioridad);
            if (inspeccionDTO.estado != null)
                if (!tipoUsuario.equals(Usuario.SOPORTE))
                    inspeccionDb.setEstado(inspeccionDTO.estado);
            if (inspeccionDTO.hi != null && inspeccionDTO.hi.size() > 0){
                HistorialInspeccionDTO hiDTO = inspeccionDTO.hi.get(0);
                HistorialInspeccion hiDb = new HistorialInspeccion();
                hiDb.setInspeccion(inspeccionDb);
                hiDb.setContenido(hiDTO.contenido);
                hiDb.setEstado(hiDTO.estado);
                hiDb.setUsuario(new Usuario(inspeccionDTO.responsable.id));
                hiDb.save();
                Logger.info("Se registro historial de inspeccion correctamente");
            }else{
                Logger.info("No se recibio historial de inspeccion");
            }
            Logger.debug("Inspeccion compartida? " + inspeccionDTO.compartido);

            inspeccionDb.update();
            map.put("respuesta", "actualizacion-exitosa");
        }else{
            Logger.info("No se encontro inspeccion con Id : " + inspeccionDTO.id);
            map.put("respuesta", "inspeccion-no-existe");
        }
        return map;
    }

    public static List<Map<String, Object>> getInspeccionesPendientes(){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesProcObs();
        return parseListMap(inspecciones);
    }

    public static List<Map<String, Object>> descargarInspeccionesSync(Long responsableId, Long minaId){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesSincronizadasCompartidas(responsableId, minaId);
        return parseListMapSync(inspecciones);
    }

    public static List<Map<String, Object>> descargarInspeccionesFinalizadasResponsableId(Long responsableId){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesFinalizadasResponsableId(responsableId);
        return parseListMap(inspecciones);
    }

    private static List<Map<String, Object>> parseListMapSync(List<Inspeccion> inspecciones){
        List<Map<String, Object>> maps = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> {
            if (inspeccion.getEstado().equals(Inspeccion.FINALIZADA)){
                Map<String, Object> map = new HashMap<>();
                map.put("inspeccion", new InspeccionDTO(inspeccion));
                maps.add(map);
            }else{
                List<HistorialInspeccionDTO> hIs = new ArrayList<>();
                HistorialInspeccion hI = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());
                if (hI != null){
                    hIs.add(new HistorialInspeccionDTO(hI));
                }
                Map<String, Object> map = new HashMap<>();
                map.put("inspeccion", new InspeccionDTO(inspeccion, hIs));
                maps.add(map);
            }
        });
        Logger.info(maps.toString());
        return maps;
    }

    private static List<Map<String, Object>> parseListMap(List<Inspeccion> inspecciones){
        List<Map<String, Object>> maps = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> {
            List<HistorialInspeccionDTO> hIs = new ArrayList<>();
            HistorialInspeccion hI = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());
            if (hI != null){
                hIs.add(new HistorialInspeccionDTO(hI));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("inspeccion", new InspeccionDTO(inspeccion, hIs));
            maps.add(map);
        });
        Logger.info(maps.toString());
        return maps;
    }

    public static Map<String, Object> eliminarInspeccion(Long id, String tipoUsuario){
        Map<String, Object> map = new HashMap<>();
        if (tipoUsuario.equals(Usuario.ADMINISTRADOR) || tipoUsuario.equals(Usuario.SUPERVISOR) ||  tipoUsuario.equals(Usuario.SOPORTE)){
            Inspeccion inspeccionDb = Inspeccion.find.byId(id);
            if(inspeccionDb != null){
                inspeccionDb.setEliminado(true);
                inspeccionDb.update();
                map.put("result", "inspeccion-eliminada");
            }else{
                map.put("result", "inspeccion-no-existe");
            }
        }else{
            map.put("result", "usuario-invalido");
        }
        return map;
    }

    public static List<InspeccionDTO> getInspecciones(){
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        List<Inspeccion> inspecciones = Inspeccion.getInspecciones();
        inspecciones.stream().forEach(inspeccion -> {
            List<HistorialInspeccionDTO> hisDTO = getHistorialInspeccionDTO(inspeccion.id);
            inspeccionDTOs.add(new InspeccionDTO(inspeccion, hisDTO));
        });
        return inspeccionDTOs;
    }

    public static InspeccionDTO getInspeccionxId(Long id){
        Inspeccion inspeccion = Inspeccion.find.byId(id);
        List<HistorialInspeccionDTO> hisDTO = new ArrayList<>();
        if (inspeccion != null){
            hisDTO.add(new HistorialInspeccionDTO(HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId())));
        }
        return new InspeccionDTO(inspeccion, hisDTO, getUsuariosHistorialInspeccion(inspeccion.id));
    }

    public static List<InspeccionDTO> getInspeccionesxEstado(String estado){
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxEstado(estado);
        inspecciones.stream().forEach(inspeccion -> {
            List<HistorialInspeccionDTO> hisDTO = getHistorialInspeccionDTO(inspeccion.id);
            inspeccionDTOs.add(new InspeccionDTO(inspeccion, hisDTO));
        });
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxTipo(String tipo){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxTipo(tipo);
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxMina(Long minaId){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxMina(minaId);
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxModelo(Long modeloId){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxModelo(modeloId);
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxConsultaLinea(ConsultaLineaDTO cDto, Long usuarioId, String tipoUsuario, Long minaId){
        List<Inspeccion> inspecciones;
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        if (cDto.tipo.equals(Inspeccion.MANTENIMIENTO_CORRECTIVO)) {
            if (tipoUsuario.equals(Usuario.SUPERVISOR_CLIENTE)){
                inspecciones = Inspeccion.getInspeccionesConsultaLineaCorrectivoClienteMina(cDto);
            }else{
                inspecciones = Inspeccion.getInspeccionesConsultaLineaCorrectivo(cDto);
            }
            List<Long> idInspeccionesCerradas = new ArrayList<>();
            inspecciones.stream().forEach(inspeccion -> {
                if (inspeccion.inspeccionCerrada != null){
                    idInspeccionesCerradas.add(inspeccion.inspeccionCerrada.getId());
                }
                inspeccionDTOs.add(new InspeccionDTO(inspeccion));
            });
            List<Inspeccion> inspeccionesNoCerradas = Inspeccion.getInspeccionesAbiertasCorrectivas(cDto, idInspeccionesCerradas);
            inspeccionesNoCerradas.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        }else {
            if (tipoUsuario.equals(Usuario.SUPERVISOR_CLIENTE)){
                inspecciones = Inspeccion.getInspeccionesConsultaLineaClienteMina(cDto);
            }else{
                inspecciones = Inspeccion.getInspeccionesConsultaLinea(cDto);
            }
            inspecciones.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        }
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxCategoriaEquipo(Long categoriaId){
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxCategoriaEquipo(categoriaId);
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        inspecciones.stream().forEach(inspeccion -> inspeccionDTOs.add(new InspeccionDTO(inspeccion)));
        return inspeccionDTOs;
    }

    public static List<InspeccionDTO> getInspeccionesxEquipo(Long equipoId){
        List<InspeccionDTO> inspeccionDTOs = new ArrayList<>();
        List<Inspeccion> inspecciones = Inspeccion.getInspeccionesxEquipo(equipoId);
        inspecciones.stream().forEach(inspeccion -> {
            List<HistorialInspeccionDTO> hisDTO = getHistorialInspeccionDTO(inspeccion.id);
            inspeccionDTOs.add(new InspeccionDTO(inspeccion, hisDTO));
        });
        return inspeccionDTOs;
    }

    private static List<HistorialInspeccionDTO> getHistorialInspeccionDTO(Long inspeccionId){
        List<HistorialInspeccionDTO> hisDTO = new ArrayList<>();
        List<HistorialInspeccion> his = HistorialInspeccion.getHistorialInspeccion(inspeccionId);
        his.stream().forEach(historialInspeccion -> hisDTO.add(new HistorialInspeccionDTO(historialInspeccion)));
        return hisDTO;
    }

    private static List<Map<String, Object>> getUsuariosHistorialInspeccion(Long inspeccionId){
        List<Map<String, Object>> maps = new ArrayList<>();
        List<HistorialInspeccion> his = HistorialInspeccion.getHistorialInspeccion(inspeccionId);
        his.stream().forEach(historialInspeccion -> {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("usuario", new UsuarioBaseDTO(historialInspeccion.getUsuario()));
            map.put("fecha_modificacion", Util.parseDateTime(historialInspeccion.getFechaCreacion()));
            maps.add(map);


        });
        return maps;
    }

    public static Map<String, Object> getConfiguracionInicial(){
        Map<String, Object> map = new HashMap<>();

        List<Categoria> categorias = Categoria.getCategorias();
        List<Modelo> modelos = Modelo.getModelos();
        List<Mina> minas = Mina.getMinas();
        List<Superintendencia> superintendencias = Superintendencia.getSuperintendencias();
        List<Fur> furs= Fur.getFurs();
        List<Equipo> equipos= Equipo.getEquipos();

        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();
        List<ModeloDTO> modeloDTOs = new ArrayList<>();
        List<MinaDTO> minaDTOs = new ArrayList<>();
        List<SuperintendenciaDTO> superintendenciaDTOs = new ArrayList<>();
        List<FurDTO> furDTOs= new ArrayList<>();
        List<EquipoDTO> equipoDTOs = new ArrayList<>();

        categorias.stream().forEach(categoria -> categoriaDTOs.add(new CategoriaDTO(categoria)));
        modelos.stream().forEach(modelo -> modeloDTOs.add(new ModeloDTO(modelo)));
        minas.stream().forEach(mina -> minaDTOs.add(new MinaDTO(mina)));
        superintendencias.stream().forEach(superintendencia -> superintendenciaDTOs.add(new SuperintendenciaDTO(superintendencia)));
        furs.stream().forEach(fur -> {
            List<SeccionFurDTO> seccionDTOs = SeccionService.getSeccionesFur(fur.getId());
            furDTOs.add(new FurDTO(fur, seccionDTOs));
        });
        equipos.stream().forEach(equipo -> equipoDTOs.add(new EquipoDTO(equipo)));

        map.put("categorias", categoriaDTOs);
        map.put("modelos", modeloDTOs);
        map.put("minas", minaDTOs);
        map.put("superintendencias", superintendenciaDTOs);
        map.put("formatos", furDTOs);
        map.put("equipos", equipoDTOs);

        return map;
    }

    public static File generatePdfInspeccion(Long inspeccionId){

        ObjectMapper mapper = new ObjectMapper();

        try
        {
            Inspeccion inspeccion = Inspeccion.find.byId(inspeccionId);

            HistorialInspeccion hi = HistorialInspeccion.getUltimoHistorialInspeccion(inspeccion.getId());

            CMYKColor white = new CMYKColor(0, 0, 0, 0);
            CMYKColor black = new CMYKColor(0, 0, 0, 253);

            BaseColor blue = new BaseColor(79, 129, 189);
            BaseColor blueLite = new BaseColor(219, 229, 241);

            Font whiteHeaderFontG = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, white);
            Font whiteHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, white);
            Font blackTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, black);
            Font blackTextFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, black);

            Document document = new Document();

            String filename = Play.application().configuration().getString("rootFolderFile") + "inspeccion_preventiva.pdf";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            writer.setStrictImageSequence(true);
            document.open();

            /*CABECERA DEL DOCUMENTO*/

            PdfPTable cabDoc = new PdfPTable(3);
            cabDoc.setWidthPercentage(100);
            cabDoc.setSpacingAfter(10f);
            float[] columCD = {0.2f, 1f, 0.2f};
            cabDoc.setWidths(columCD);

            Image logoMarco = Image.getInstance(Play.application().configuration().getString("rootFolderFile") + "logo-marco.png");
            logoMarco.scaleAbsolute(50, 30);

            PdfPCell cellLMar = new PdfPCell(logoMarco);
            cellLMar.setBorderColor(blue);
            cellLMar.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLMar.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cabDoc.addCell(cellLMar);

            PdfPCell cellNFor = new PdfPCell(new Paragraph(inspeccion.getFur().getNombre(), whiteHeaderFontG));
            cellNFor.setBorderColor(blue);
            cellNFor.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNFor.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellNFor.setBackgroundColor(blue);
            cabDoc.addCell(cellNFor);

            if (inspeccion.getEquipo().getSuperintendencia().getMina().getLogo() != null){
                Image logoMina = Image.getInstance(inspeccion.getEquipo().getSuperintendencia().getMina().getLogo());
                logoMina.scaleAbsolute(50, 30);

                PdfPCell cellLMin = new PdfPCell(logoMina);
                cellLMin.setBorderColor(blue);
                cellLMin.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellLMin.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cabDoc.addCell(cellLMin);
            }

            document.add(cabDoc);

            /*FIN CABECERA DEL DOCUMENTO*/

            /*SECCION DATOS DE LA INSPECCION*/

            float[] wDatosInspeccion = {0.5f, 1f};

            PdfPTable tabOt = new PdfPTable(2);
            tabOt.setWidthPercentage(100);
            tabOt.setWidths(wDatosInspeccion);

            PdfPCell lblOt = new PdfPCell(new Paragraph("OT", blackTitleFont));
            lblOt.setBorderColor(blue);
            lblOt.setPaddingLeft(5);
            lblOt.setVerticalAlignment(Element.ALIGN_MIDDLE);
            lblOt.setBackgroundColor(blueLite);
            tabOt.addCell(lblOt);

            PdfPCell lblValOt = new PdfPCell(new Paragraph(Util.capitalize(inspeccion.getOrdenTrabajo()), blackTextFont));
            lblValOt.setBorderColor(blue);
            lblValOt.setPaddingLeft(5);
            lblValOt.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabOt.addCell(lblValOt);

            document.add(tabOt);

            //-------------------------------------

            PdfPTable tabFe = new PdfPTable(2);
            tabFe.setWidthPercentage(100);
            tabFe.setWidths(wDatosInspeccion);

            PdfPCell lblFe = new PdfPCell(new Paragraph("FECHA", blackTitleFont));
            lblFe.setBorderColor(blue);
            lblFe.setPaddingLeft(5);
            lblFe.setVerticalAlignment(Element.ALIGN_MIDDLE);
            lblFe.setBackgroundColor(blueLite);
            tabFe.addCell(lblFe);

            PdfPCell lblValFe = new PdfPCell(new Paragraph(Util.parseDateTime(inspeccion.getFechaCreacion()), blackTextFont));
            lblValFe.setBorderColor(blue);
            lblValFe.setPaddingLeft(5);
            lblValFe.setVerticalAlignment(Element.ALIGN_MIDDLE);
            //lblValFe.setBackgroundColor(blue);
            tabFe.addCell(lblValFe);

            document.add(tabFe);

            //----------------------------------------

            PdfPTable tabRe = new PdfPTable(2);
            tabRe.setWidthPercentage(100);
            tabRe.setSpacingAfter(10f);
            tabRe.setWidths(wDatosInspeccion);

            PdfPCell lblRe = new PdfPCell(new Paragraph("RESPONSABLE", blackTitleFont));
            lblRe.setBorderColor(blue);
            lblRe.setPaddingLeft(5);
            lblRe.setVerticalAlignment(Element.ALIGN_MIDDLE);
            lblRe.setBackgroundColor(blueLite);
            tabRe.addCell(lblRe);

            PdfPCell lblValRe = new PdfPCell(new Paragraph(inspeccion.getResponsable().getNombres() + " " + inspeccion.getResponsable().getApellidos(), blackTextFont));
            lblValRe.setBorderColor(blue);
            lblValRe.setPaddingLeft(5);
            lblValRe.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabRe.addCell(lblValRe);

            document.add(tabRe);

            /*FIN SECCION DATOS DE LA INSPECCION*/

            /*SECCION DATOS DE MAQUINA*/

            PdfPTable cabDE = new PdfPTable(1);
            cabDE.setWidthPercentage(100);
            cabDE.setSpacingAfter(0.5f);

            PdfPCell cellCab = new PdfPCell(new Paragraph("DATOS DE MAQUINA", whiteHeaderFont));
            cellCab.setBorderColor(BaseColor.BLUE);
            cellCab.setPaddingLeft(20);
            cellCab.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellCab.setBackgroundColor(blue);
            cabDE.addCell(cellCab);

            document.add(cabDE);

            PdfPTable TabDatosEquipo = new PdfPTable(6);
            TabDatosEquipo.setWidthPercentage(100);
            float[] columSE = {1f, 1f, 1f, 1f, 1f, 1f};
            TabDatosEquipo.setWidths(columSE);

            PdfPCell lblEquipo = new PdfPCell(new Paragraph("EQUIPO", blackTitleFont));
            lblEquipo.setBorderColor(blue);
            lblEquipo.setPaddingLeft(5);
            lblEquipo.setBackgroundColor(blueLite);

            PdfPCell lblNEquipo = new PdfPCell(new Paragraph(inspeccion.getEquipo().getNombre(), blackTextFont));
            lblNEquipo.setBorderColor(blue);
            lblNEquipo.setPaddingLeft(5);

            PdfPCell lblModelo = new PdfPCell(new Paragraph("MODELO", blackTitleFont));
            lblModelo.setBorderColor(blue);
            lblModelo.setPaddingLeft(5);
            lblModelo.setBackgroundColor(blueLite);

            PdfPCell lblNModelo = new PdfPCell(new Paragraph(inspeccion.getEquipo().getModelo().getNombre(), blackTextFont));
            lblNModelo.setBorderColor(blue);
            lblNModelo.setPaddingLeft(5);

            PdfPCell lblHorometro = new PdfPCell(new Paragraph("HOROMETRO", blackTitleFont));
            lblHorometro.setBorderColor(blue);
            lblHorometro.setPaddingLeft(5);
            lblHorometro.setBackgroundColor(blueLite);

            PdfPCell lblCantHorometro = new PdfPCell(new Paragraph(inspeccion.getHorometro().toString(), blackTextFont));
            lblCantHorometro.setBorderColor(blue);
            lblCantHorometro.setPaddingLeft(5);

            TabDatosEquipo.addCell(lblEquipo);
            TabDatosEquipo.addCell(lblNEquipo);
            TabDatosEquipo.addCell(lblModelo);
            TabDatosEquipo.addCell(lblNModelo);
            TabDatosEquipo.addCell(lblHorometro);
            TabDatosEquipo.addCell(lblCantHorometro);

            document.add(TabDatosEquipo);


            List<Map<String, Object>> secciones = mapper.convertValue(hi.getContenido().get("data"), ArrayList.class);

            for (Map<String, Object> seccion: secciones) {
                try {
                    PdfPTable secCab = new PdfPTable(1);
                    secCab.setWidthPercentage(100);
                    secCab.setSpacingAfter(0.5f);
                    secCab.setSpacingBefore(10f);
                    PdfPCell secCel = new PdfPCell(new Paragraph(seccion.get("orden") + ". " + seccion.get("nombre").toString(), whiteHeaderFont));
                    secCel.setBorderColor(BaseColor.BLUE);
                    secCel.setPaddingLeft(20);
                    secCel.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    secCel.setBackgroundColor(blue);
                    secCab.addCell(secCel);
                    document.add(secCab);
                    Map<String, Object> dataSeccion = mapper.convertValue(seccion.get("contenido"), Map.class);
                    Map<String, Object> tabla = mapper.convertValue(dataSeccion.get("data"), Map.class);
                    List<Map<String, Object>> filas = mapper.convertValue(tabla.get("content"), ArrayList.class);


                    List<PdfPTable> arrTable = new ArrayList<>();

                    int[] maxSizeColumn = null;

                    int indiceFila = 0;
                    for (Map<String, Object> fila: filas) {

                        List<Map<String, Object>> columnas = mapper.convertValue(fila.get("content"), ArrayList.class);
                        PdfPTable tabRep = new PdfPTable(columnas.size());

                        if (maxSizeColumn == null){
                            maxSizeColumn = new int[columnas.size()];
                            for (int c = 0; c < columnas.size(); c++) {
                                maxSizeColumn[c] = 1;
                            }
                        }

                        int indiceColumna = 0;
                        for (Map<String, Object> columna: columnas) {

                            Map<String, Object> objeto = mapper.convertValue(columna.get("content"), Map.class);
                            Paragraph para = null;
                            Image optIco = null;

                            PdfPCell celldin = new PdfPCell(new Paragraph("-"));

                            if (objeto.containsKey("obj")){
                                if (objeto.get("obj").toString().equals("label")){
                                    String cadena = "";
                                    if (objeto.containsKey("value")){
                                        cadena = objeto.get("value").toString();
                                    }
                                    para = new Paragraph(cadena, indiceFila == 0 ? blackTitleFont: blackTextFont);
                                    if(cadena.length() > maxSizeColumn[indiceColumna]){
                                        maxSizeColumn[indiceColumna] = cadena.length();
                                    }

                                }else if (objeto.get("obj").toString().equals("input-option")){
                                    String cadena = "";
                                    if (objeto.containsKey("value")){
                                        cadena = objeto.get("value").toString();
                                    }
                                    para = new Paragraph(cadena, blackTextFont);
                                    if(cadena.length() > maxSizeColumn[indiceColumna]){
                                        maxSizeColumn[indiceColumna] = cadena.length();
                                    }
                                }else if (objeto.get("obj").toString().equals("input-textnum")){
                                    String cadena = "";
                                    if (objeto.containsKey("value")){
                                        cadena = objeto.get("value").toString();
                                    }
                                    para = new Paragraph(cadena, blackTextFont);
                                    if(cadena.length() > maxSizeColumn[indiceColumna]){
                                        maxSizeColumn[indiceColumna] = cadena.length();
                                    }
                                }else if (objeto.get("obj").toString().equals("input-text")){
                                    String cadena = "";
                                    if (objeto.containsKey("value")){
                                        cadena = objeto.get("value").toString();
                                    }
                                    para = new Paragraph(cadena, blackTextFont);
                                    if(cadena.length() > maxSizeColumn[indiceColumna]){
                                        maxSizeColumn[indiceColumna] = cadena.length();
                                    }
                                }else if (objeto.get("obj").toString().equals("input-num")){
                                    String cadena = "";
                                    if (objeto.containsKey("value")){
                                        cadena = objeto.get("value").toString();
                                    }
                                    para = new Paragraph(cadena, blackTextFont);
                                    if(cadena.length() > maxSizeColumn[indiceColumna]){
                                        maxSizeColumn[indiceColumna] = cadena.length();
                                    }
                                }else if (objeto.get("obj").toString().equals("input-check")){
                                    String checked = "No";
                                    if (objeto.containsKey("checked")){
                                        Boolean isChecked = ((Boolean) objeto.get("checked")).booleanValue();
                                        if (isChecked){
                                            checked = "Si";
                                        }
                                    }
                                    para = new Paragraph(checked, blackTextFont);
                                    maxSizeColumn[indiceColumna] = 2;
                                }else if(objeto.get("obj").toString().equals("input-option-ico")){
                                    String value = objeto.get("value").toString();
                                    switch (value){
                                        case "bueno":
                                            optIco = Image.getInstance(Play.application().configuration().getString("rootFolderFile") + "check.png");
                                            break;
                                        case "alerta":
                                            optIco = Image.getInstance(Play.application().configuration().getString("rootFolderFile") +  "alert.png");
                                            break;
                                        case "malo":
                                            optIco = Image.getInstance(Play.application().configuration().getString("rootFolderFile") + "cancel.png");
                                            break;
                                        default:
                                            optIco = Image.getInstance(Play.application().configuration().getString("rootFolderFile") + "palert.png");
                                            break;
                                    }
                                    maxSizeColumn[indiceColumna] = 10;
                                }
                            }

                            if (para != null){
                                celldin = new PdfPCell(para);
                                if (indiceFila == 0){
                                    celldin.setHorizontalAlignment(Element.ALIGN_CENTER);
                                }
                            }else if (optIco != null){
                                optIco.scaleAbsolute(10, 10);
                                optIco.setAlignment(Image.MIDDLE);
                                celldin = new PdfPCell(optIco);
                                celldin.setHorizontalAlignment(Element.ALIGN_CENTER);
                            }
                            //PdfPCell celldin = new PdfPCell(para);
                            celldin.setBorderColor(blue);
                            celldin.setPadding(5);

                            if (indiceFila == 0)
                                celldin.setBackgroundColor(blueLite);
                            tabRep.addCell(celldin);
                            //}
                            indiceColumna++;

                        }

                        arrTable.add(tabRep);


                        indiceFila++;
                    }

                    int CELDA_CHICA = 6;
                    int CELDA_MEDIANA = 30;

                    for (PdfPTable pdt : arrTable){
                        pdt.setWidthPercentage(100);
                        float[] cdw = new float[maxSizeColumn.length];
                        for (int i = 0; i < maxSizeColumn.length; i++) {
                            if (maxSizeColumn[i] < CELDA_CHICA){
                                cdw[i] = 0.15f;
                            }else if(maxSizeColumn[i] > CELDA_CHICA && maxSizeColumn[i] < CELDA_MEDIANA){
                                cdw[i] = 0.4f;
                            }else{
                                cdw[i] = 1f;
                            }
                        }
                        pdt.setWidths(cdw);
                        document.add(pdt);
                    }

                    if (seccion.containsKey("mostrarImagenAyuda")){
                        Boolean mostrarImagenAyuda = ((Boolean) seccion.get("mostrarImagenAyuda")).booleanValue();
                        if (mostrarImagenAyuda){
                            if (seccion.containsKey("imagenAyuda")){
                                Image imagenAyuda = Image.getInstance(DatatypeConverter.parseBase64Binary(seccion.get("imagenAyuda").toString()));
                                if (imagenAyuda.getWidth() > 500f){
                                    imagenAyuda.scaleAbsoluteWidth(500f);
                                }
                                imagenAyuda.setAlignment(Image.MIDDLE);
                                imagenAyuda.setScaleToFitHeight(false);

                                PdfPTable tabImagenayuda = new PdfPTable(1);
                                tabImagenayuda.setWidthPercentage(100);
                                float[] cdw = new float[1];
                                for (int i = 0; i < 1; i++) {
                                    cdw[i] = 1f;
                                }
                                tabImagenayuda.setWidths(cdw);

                                PdfPCell cellIA = new PdfPCell(imagenAyuda);
                                cellIA.setBorder(Rectangle.NO_BORDER);
                                cellIA.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tabImagenayuda.addCell(cellIA);

                                document.add(tabImagenayuda);
                            }else{
                                Logger.debug("No tiene imagen ayuda");
                            }
                        }
                    }else{
                        Logger.debug("No debe mostrar imagen ayuda");
                    }

                    //IMAGENES FOR TEMPORAL

                    if (tabla.containsKey("pictures")){
                        List<Map<String, Object>> fotos = mapper.convertValue(tabla.get("pictures"), ArrayList.class);
                        if (fotos.size() > 0){

                            if (fotos.size() < 2){
                                PdfPTable tabImagenes = new PdfPTable(fotos.size());
                                float[] cdw = new float[fotos.size()];
                                for (int i = 0; i < fotos.size(); i++) {
                                    cdw[i] = 1f;
                                }

                                tabImagenes.setWidthPercentage(100f);
                                tabImagenes.setSpacingBefore(10f);
                                tabImagenes.setWidths(cdw);

                                for (Map<String, Object> foto : fotos){
                                    if (foto.containsKey("src")){

                                        Image imagenFoto = Image.getInstance(DatatypeConverter.parseBase64Binary(foto.get("src").toString().split(",")[1]));
                                        imagenFoto.scaleAbsolute(210f, 210f);
                                        imagenFoto.setScaleToFitHeight(false);

                                        PdfPTable content = new PdfPTable(1);

                                        PdfPCell cellFoto = new PdfPCell(imagenFoto);
                                        cellFoto.setBorder(Rectangle.NO_BORDER);
                                        cellFoto.setPadding(2.5f);
                                        cellFoto.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        content.addCell(cellFoto);

                                        PdfPCell cellSub = new PdfPCell(new Phrase(Util.capitalize(foto.get("sub").toString()), blackTextFont));
                                        cellSub.setBorder(Rectangle.NO_BORDER);
                                        cellSub.setPadding(2.5f);
                                        cellSub.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        content.addCell(cellSub);

                                        PdfPCell cellBody = new PdfPCell(content);
                                        cellBody.setBorder(Rectangle.NO_BORDER);

                                        tabImagenes.addCell(cellBody);

                                        //tabImagenes.addCell(content);

                                    }
                                }

                                document.add(tabImagenes);

                            }else{

                                int residuo = fotos.size() % 2;
                                Logger.info("Residuo : " + residuo);

                                int filasFotos = 0;
                                if (fotos.size() >= 2){
                                    filasFotos = fotos.size() / 2;
                                }

                                Logger.debug("Fila fotos : " + filasFotos);

                                for (int i = 0; i < filasFotos; i++) {

                                    PdfPTable tabImagenes = new PdfPTable(2);
                                    float[] cdw = new float[2];
                                    for (int j = 0; j < 2; j++) {
                                        cdw[j] = 1f;
                                    }

                                    tabImagenes.setWidthPercentage(100f);
                                    tabImagenes.setSpacingBefore(10f);
                                    tabImagenes.setWidths(cdw);

                                    int indiceFinal = (i+1)*2;

                                    for (int indiceInicial = (i*2) ; indiceInicial < indiceFinal; indiceInicial++) {

                                        Map<String, Object> foto = fotos.get(indiceInicial);

                                        if (foto.containsKey("src")){
                                            Image imagenFoto = Image.getInstance(DatatypeConverter.parseBase64Binary(foto.get("src").toString().split(",")[1]));
                                            imagenFoto.scaleAbsolute(210f, 210f);
                                            imagenFoto.setScaleToFitHeight(false);

                                            PdfPTable content = new PdfPTable(1);

                                            PdfPCell cellFoto = new PdfPCell(imagenFoto);
                                            cellFoto.setBorder(Rectangle.NO_BORDER);
                                            cellFoto.setPadding(2.5f);
                                            cellFoto.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            content.addCell(cellFoto);

                                            PdfPCell cellSub = new PdfPCell(new Phrase(Util.capitalize(foto.get("sub").toString()), blackTextFont));
                                            cellSub.setBorder(Rectangle.NO_BORDER);
                                            cellSub.setPadding(2.5f);
                                            cellSub.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            content.addCell(cellSub);

                                            PdfPCell cellBody = new PdfPCell(content);
                                            cellBody.setBorder(Rectangle.NO_BORDER);

                                            tabImagenes.addCell(cellBody);

                                        }
                                    }
                                    document.add(tabImagenes);
                                }

                                if (residuo > 0){

                                    Logger.debug("El residuo es mayor a 0");

                                    PdfPTable tabImagenes = new PdfPTable(residuo);
                                    float[] cdw = new float[residuo];
                                    for (int i = 0; i < residuo; i++) {
                                        cdw[i] = 1f;
                                    }

                                    tabImagenes.setWidthPercentage(100f);
                                    tabImagenes.setSpacingBefore(10f);
                                    tabImagenes.setWidths(cdw);

                                    for (int i = filasFotos * 2; i < (filasFotos * 2) + residuo; i++) {

                                        Map<String, Object> foto = fotos.get(i);

                                        if (foto.containsKey("src")){

                                            Image imagenFoto = Image.getInstance(DatatypeConverter.parseBase64Binary(foto.get("src").toString().split(",")[1]));
                                            imagenFoto.scaleAbsolute(210f, 210f);
                                            imagenFoto.setScaleToFitHeight(false);

                                            PdfPTable content = new PdfPTable(1);

                                            PdfPCell cellFoto = new PdfPCell(imagenFoto);
                                            cellFoto.setBorder(Rectangle.NO_BORDER);
                                            cellFoto.setPadding(2.5f);
                                            cellFoto.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            content.addCell(cellFoto);

                                            PdfPCell cellSub = new PdfPCell(new Phrase(Util.capitalize(foto.get("sub").toString()), blackTextFont));
                                            cellSub.setBorder(Rectangle.NO_BORDER);
                                            cellSub.setPadding(2.5f);
                                            cellSub.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            content.addCell(cellSub);

                                            PdfPCell cellBody = new PdfPCell(content);
                                            cellBody.setBorder(Rectangle.NO_BORDER);

                                            tabImagenes.addCell(cellBody);
                                        }

                                        if (foto.containsKey("sub")){

                                            Logger.info("DESCRIPCION DE LA FOTO ::: " + foto.get("sub").toString());
                                        }

                                    }

                                    document.add(tabImagenes);

                                }else{

                                    Logger.debug("El residuo es menor = a 0");

                                }
                            }
                        }
                    }


                    //a pedido de marcello se cancela el salto de pagina
                    //document.newPage();
                }catch (DocumentException e){
                    e.printStackTrace();
                }
            }
            document.close();
            writer.close();

            return new File(filename);

        } catch (DocumentException e){
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getSCLUltimosDoceMeses(SeguimientoDTO sdto){
        List<Date> fechasReporte = new ArrayList<>();
        for (int i = 0; i < 12 ; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -(i));
            fechasReporte.add(cal.getTime());
        }
        return Inspeccion.getCantidadInspeccionesSCLXMes(fechasReporte, sdto.minaId);
    }

    public static Map<String, Object> getCorrectivoUltimosDoceMeses(SeguimientoDTO sdto){
        List<Date> fechasReporte = new ArrayList<>();
        for (int i = 0; i < 12 ; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -(i));
            fechasReporte.add(cal.getTime());
        }
        return Inspeccion.getCantidadInspeccionesCorrectivas(fechasReporte, sdto.minaId);
    }

}
