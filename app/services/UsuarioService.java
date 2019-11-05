package services;

import entities.UsuarioBaseDTO;
import entities.UsuarioDTO;
import models.Inspeccion;
import models.Mina;
import models.Usuario;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.Play;
import util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService{

    public static Map<String, Object> registrarUsuario(UsuarioDTO usuarioDTO){
        Map<String, Object> map = new HashMap<>();

        Usuario usuarioDb = new Usuario();
        usuarioDb.setTipoUsuario(usuarioDTO.tipoUsuario);
        usuarioDb.setNombres(usuarioDTO.nombres);
        usuarioDb.setApellidos(usuarioDTO.apellidos);
        usuarioDb.setCorreo(usuarioDTO.correo);
        usuarioDb.setUsuario(usuarioDTO.usuario);
        usuarioDb.setClave(BCrypt.hashpw(usuarioDTO.clave, BCrypt.gensalt()));
        usuarioDb.setTelefono(usuarioDTO.telefono);
        usuarioDb.setEstado(Usuario.ACTIVO);
        if (usuarioDTO.mina != null && usuarioDTO.mina.id != null)
            usuarioDb.setMina(new Mina(usuarioDTO.mina.id));
        else
            Logger.debug("Este usuario no esta enviando mina");
        usuarioDb.save();
        map.put("respuesta", "registro-exitoso");
        Logger.debug("Se grabo el usuario  :::  " + usuarioDb.toString());
        return map;
    }

    public static void actualizarUsuario(UsuarioDTO usuarioDTO, Long usuarioId){
        Usuario usuarioDb = Usuario.find.byId(usuarioDTO.id);
        if(usuarioDb != null){
            if (usuarioDTO.nombres != null)
                usuarioDb.setNombres(usuarioDTO.nombres);
            if (usuarioDTO.apellidos != null)
                usuarioDb.setApellidos(usuarioDTO.apellidos);
            if (usuarioDTO.correo != null)
                usuarioDb.setCorreo(usuarioDTO.correo);
            if (usuarioDTO.clave != null)
                usuarioDb.setClave(BCrypt.hashpw(usuarioDTO.clave, BCrypt.gensalt()));
            if (usuarioDTO.telefono != null)
                usuarioDb.setTelefono(usuarioDTO.telefono);
            if (usuarioDTO.estado != null)
                usuarioDb.setEstado(usuarioDTO.estado);
            if (usuarioDTO.tipoUsuario != null) {
                if (usuarioDb.getId().equals(usuarioId)){
                    if (!usuarioDb.getTipoUsuario().equals(Usuario.ADMINISTRADOR)){
                        usuarioDb.setTipoUsuario(usuarioDTO.tipoUsuario);
                    }
                }else{
                    usuarioDb.setTipoUsuario(usuarioDTO.tipoUsuario);
                }
            }
            if (usuarioDTO.mina != null && usuarioDTO.mina.id != null)
                usuarioDb.setMina(new Mina(usuarioDTO.mina.id));
            usuarioDb.update();
        }
    }

    public static Map<String, Object> eliminarUsuario(Long id){
        Map<String, Object> map = new HashMap<>();
        Usuario usuarioDb = Usuario.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(usuarioDb != null){
            if (!Inspeccion.usuarioRealizoInspeccion(usuarioDb.getId())){
                usuarioDb.setEliminado(true);
                usuarioDb.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "usuario-en-uso");
            }
        }else{
            map.put("respuesta", "usuario-no-existe");
        }
        return map;
    }

    public static List<UsuarioBaseDTO> getUsuarios(){
        List<UsuarioBaseDTO> usuarioBaseDTOs = new ArrayList<>();
        List<Usuario> usuarios = Usuario.getUsuarios();
        usuarios.stream().forEach(usuario -> usuarioBaseDTOs.add(new UsuarioBaseDTO(usuario)));
        return usuarioBaseDTOs;
    }

    public static UsuarioBaseDTO getUsuario(Long id){
        return new UsuarioBaseDTO(Usuario.find.byId(id));
    }

    public static File generarExcelUsuarios(){
        try {
            String filename = Play.application().configuration().getString("rootFolderFile") + "usuarios.xls" ;
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

            HSSFCell cabUsuario = rowhead.createCell(0);
            HSSFCell cabNombres = rowhead.createCell(1);
            HSSFCell cabMina = rowhead.createCell(2);
            HSSFCell cabCorreo = rowhead.createCell(3);
            HSSFCell cabTelefono = rowhead.createCell(4);
            HSSFCell cabTipoUsuario = rowhead.createCell(5);
            HSSFCell cabFechaRegistro = rowhead.createCell(6);

            cabUsuario.setCellValue("USUARIO");
            cabNombres.setCellValue("NOMBRES");
            cabMina.setCellValue("MINA");
            cabCorreo.setCellValue("CORREO");
            cabTelefono.setCellValue("TELEFONO");
            cabTipoUsuario.setCellValue("TIPO DE USUARIO");
            cabFechaRegistro.setCellValue("FECHA REGISTRO");

            cabUsuario.setCellStyle(styleHead);
            cabNombres.setCellStyle(styleHead);
            cabMina.setCellStyle(styleHead);
            cabCorreo.setCellStyle(styleHead);
            cabTelefono.setCellStyle(styleHead);
            cabTipoUsuario.setCellStyle(styleHead);
            cabFechaRegistro.setCellStyle(styleHead);

            List<Usuario> usuarios = Usuario.getUsuarios();

            for (int i = 0; i < usuarios.size(); i++) {

                HSSFRow row = sheet.createRow(i+1);

                row.createCell(0).setCellValue(usuarios.get(i).getUsuario());
                row.createCell(1).setCellValue(usuarios.get(i).getNombres() + " " + usuarios.get(i).getApellidos());
                if (usuarios.get(i).getMina() != null){
                    row.createCell(2).setCellValue(usuarios.get(i).getMina().getNombre());
                }else{
                    row.createCell(2).setCellValue("-");
                }
                row.createCell(3).setCellValue(usuarios.get(i).getCorreo());
                row.createCell(4).setCellValue(usuarios.get(i).getTelefono());

                switch (usuarios.get(i).getTipoUsuario()){
                    case Usuario.ADMINISTRADOR:
                        row.createCell(5).setCellValue("Administrador");
                        break;
                    case Usuario.INSPECTOR:
                        row.createCell(5).setCellValue("Inspector");
                        break;
                    case Usuario.SOPORTE:
                        row.createCell(5).setCellValue("Soporte");
                        break;
                    case Usuario.SUPERVISOR:
                        row.createCell(5).setCellValue("Supervisor");
                        break;
                    case Usuario.SUPERVISOR_CLIENTE:
                        row.createCell(5).setCellValue("Supervisor cliente");
                        break;
                    default:
                        row.createCell(5).setCellValue("-");
                        break;
                }

                row.createCell(6).setCellValue(Util.parseDateTime(usuarios.get(i).getFechaCreacion()));
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
}
