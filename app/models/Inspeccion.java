package models;

import com.avaje.ebean.Expr;
import com.google.common.collect.Lists;
import entities.ConsultaLineaDTO;
import util.Util;

import javax.persistence.*;
import java.util.*;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "inspeccion_seq")
@Table(name = "inspecciones")
public class Inspeccion extends BaseModel {

    //TIPO DE LA INSPECCION
    public static final String INSPECCION_SCL = "IS",
            MONITOREO_TEMPERATURA = "MT",
            MANTENIMIENTO_CORRECTIVO = "MC",//ESTE ES EL TIPO DE INSPECCION QUE SE DENOMINA "EVENTOS"
            INSPECCION_ENGRANAJE = "IE";

    //ESTADO DE LA INSPECCION
    public static final String PROCESO = "P",
            OBSERVADA = "O",
            FINALIZADA = "F";

    //PRIORIDAD DE LA INSPECCION
    public static final String ALTA = "AL",
            NORMAL = "NO",
            BAJA = "BA";

    public Inspeccion(){
        super();
    }

    public Inspeccion(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'P'")
    public String estado;

    @Column(name = "tipo", columnDefinition = "varchar(2)")
    public String tipo;

    @ManyToOne
    @Column(name = "equipo")
    public Equipo equipo;

    @ManyToOne
    @Column(name = "fur")
    public Fur fur;

    @Column(name = "orden_trabajo")
    public String ordenTrabajo;

    @Column(name = "prioridad", columnDefinition = "varchar(2)")
    public String prioridad;

    @ManyToOne
    @Column(name = "responsable")
    public Usuario responsable;

    @Column(name = "resumen_actividad")
    public String resumenActividad;

    @Column(name = "observaciones")
    public String observaciones;

    @Column(name = "aprobado_soporte")
    public boolean aprobadoSoporte;

    @Column(name = "compartido")
    public boolean compartido;

    @Column(name = "horometro")
    public Long horometro;

    @OneToOne
    @Column(name = "inspeccion_cerrada")
    public Inspeccion inspeccionCerrada;

    public static Finder<Long, Inspeccion> find = new Finder<>(Inspeccion.class);

    public static List<Inspeccion> getInspeccionesProcObs(){
        List<String> expr = new ArrayList<>();
        expr.add(PROCESO);
        expr.add(OBSERVADA);
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .in("estado", expr)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesSincronizadasCompartidas(Long inspectorId, Long minaId){
        List<String> expr = new ArrayList<>();
        expr.add(PROCESO);
        expr.add(OBSERVADA);
        expr.add(FINALIZADA);
        return Inspeccion.find.where()
                .or(Expr.eq("responsable_id", inspectorId), Expr.eq("compartido", true))
                .eq("equipo.superintendencia.mina.id", minaId)
                .eq("eliminado", false)
                .in("estado", expr)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesFinalizadasResponsableId(Long responsableId){
        List<String> expr = new ArrayList<>();
        expr.add(FINALIZADA);
        return Inspeccion.find.where()
                .eq("responsable_id", responsableId)
                .eq("eliminado", false)
                .in("estado", expr)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspecciones(){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxEstado(String estado){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("estado", estado)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxTipo(String tipo){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("tipo", tipo)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxMina(Long minaId){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("equipo.superintendencia.mina.id", minaId)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxModelo(Long modeloId){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("equipo.modelo.id", modeloId)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesConsultaLinea(ConsultaLineaDTO cDto){
        return Inspeccion.find.where()
                .between("t0.fecha_creacion", Util.parseDateInicial(cDto.fechaInicial), Util.parseDateFinal(cDto.fechaFinal))
                .eq("eliminado", false)
                .eq("equipo.superintendencia.id", cDto.superintendenciaId)
                .eq("tipo", cDto.tipo)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesConsultaLineaClienteMina(ConsultaLineaDTO cDto){
        return Inspeccion.find.where()
                .between("t0.fecha_creacion", Util.parseDateInicial(cDto.fechaInicial), Util.parseDateFinal(cDto.fechaFinal))
                .eq("eliminado", false)
                .eq("equipo.superintendencia.id", cDto.superintendenciaId)
                .eq("tipo", cDto.tipo)
                .eq("aprobado_soporte", true)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesConsultaLineaCorrectivo(ConsultaLineaDTO cDto){
        return Inspeccion.find.where()
                .between("t0.fecha_creacion", Util.parseDateInicial(cDto.fechaInicial), Util.parseDateFinal(cDto.fechaFinal))
                .eq("eliminado", false)
                .eq("equipo.superintendencia.id", cDto.superintendenciaId)
                .eq("tipo", cDto.tipo)
                .not(Expr.eq("inspeccion_cerrada_id", null))
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesConsultaLineaCorrectivoClienteMina(ConsultaLineaDTO cDto){
        return Inspeccion.find.where()
                .between("t0.fecha_creacion", Util.parseDateInicial(cDto.fechaInicial), Util.parseDateFinal(cDto.fechaFinal))
                .eq("eliminado", false)
                .eq("equipo.superintendencia.id", cDto.superintendenciaId)
                .eq("tipo", cDto.tipo)
                .eq("aprobado_soporte", true)
                .not(Expr.eq("inspeccion_cerrada_id", null))
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesAbiertasCorrectivas(ConsultaLineaDTO cDto, List<Long> idInspeccionesCerradas){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("equipo.superintendencia.id", cDto.superintendenciaId)
                .eq("tipo", MANTENIMIENTO_CORRECTIVO)
                .eq("inspeccion_cerrada_id", null)
                .not(Expr.in("id", idInspeccionesCerradas))
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxCategoriaEquipo(Long categoriaId){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("equipo.modelo.categoria.id", categoriaId)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxEquipo(Long equipoId){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("equipo_id", equipoId)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static List<Inspeccion> getInspeccionesxEquipoYTipo(Long equipoId, String tipo){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("tipo", tipo)
                .eq("equipo_id", equipoId)
                .eq("inspeccion_cerrada_id", null)
                .orderBy("t0.fecha_creacion desc").findList();
    }

    public static Inspeccion getUltimaInspeccionEquipoYTipo(Long equipoId, String tipo){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("tipo", tipo)
                .eq("equipo_id", equipoId)
                .eq("inspeccion_cerrada_id", null)
                .orderBy("t0.fecha_creacion desc")
                .setMaxRows(1).findUnique();
    }

    public static Inspeccion getInspeccionCierre(Long inspeccionId){
        return Inspeccion.find.where()
                .eq("eliminado", false)
                .eq("inspeccion_cerrada_id", inspeccionId)
                .eq("tipo", MANTENIMIENTO_CORRECTIVO)
                .findUnique();
    }

    public static boolean usuarioRealizoInspeccion(Long usuarioId){
        return Inspeccion.find.where().eq("eliminado", false)
                .eq("responsable_id", usuarioId).findRowCount() > 0;
    }

    public static boolean equipoExiste(Long equipoId){
        return Inspeccion.find.where().eq("eliminado", false)
                .eq("equipo_id", equipoId).findRowCount() > 0;
    }

    public static boolean furExiste(Long furId){
        return Inspeccion.find.where().eq("eliminado", false)
                .eq("fur_id", furId).findRowCount() > 0;
    }

    public static Map<String, Object> getCantidadInspeccionesSCLXMes(List<Date> dates, Long minaId){
        Map<String, Object> map = new HashMap<>();
        List<String> mesesSeleccionados = new ArrayList<>();
        List<Integer> inspeccionesTotalesXMes = new ArrayList<>();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        dates.stream().forEach(date -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.AM);

            Date firstDayOfMonth = calendar.getTime();

            //Logger.info("FECHA DE INCIO ::: " + firstDayOfMonth.toString());

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.AM_PM, Calendar.PM);
            Date lastDayOfMonth = calendar.getTime();

            //Logger.info("FECHA DE FIN ::: " + lastDayOfMonth.toString());
            //Logger.info("---------------------------------");

            calendar.setTime(date);

            String mes = meses[calendar.get(Calendar.MONTH)];

            int cantInspecciones = Inspeccion.find.where()
                    .eq("t0.eliminado", false)
                    .between("t0.fecha_creacion", firstDayOfMonth, lastDayOfMonth)
                    .eq("equipo.superintendencia.mina.id", minaId)
                    .eq("t0.tipo", INSPECCION_SCL).findRowCount();

            mesesSeleccionados.add(mes);
            inspeccionesTotalesXMes.add(cantInspecciones);
        });
        map.put("meses", Lists.reverse(mesesSeleccionados));
        map.put("inspecciones", Lists.reverse(inspeccionesTotalesXMes));
        return map;
    }

    public static Map<String, Object> getCantidadInspeccionesCorrectivas(List<Date> dates, Long minaId){
        Map<String, Object> map = new HashMap<>();
        String[] series = {"Apertura", "Cierre"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        List<String> mesesSeleccionados = new ArrayList<>();
        List<Integer> inspeccionesAbiertaXMes = new ArrayList<>();
        List<Integer> inspeccionesCerradasXMes = new ArrayList<>();
        dates.stream().forEach(date -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfMonth = calendar.getTime();

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date lastDayOfMonth = calendar.getTime();

            String mes = meses[calendar.get(Calendar.MONTH)];

            int cantInspeccionesAbiertas = Inspeccion.find.where()
                    .eq("t0.eliminado", false)
                    .between("t0.fecha_creacion", firstDayOfMonth, lastDayOfMonth)
                    .eq("equipo.superintendencia.mina.id", minaId)
                    .eq("inspeccion_cerrada_id", null)
                    .eq("tipo", MANTENIMIENTO_CORRECTIVO).findRowCount();

            int cantInspeccionesCerradas = Inspeccion.find.where()
                    .eq("t0.eliminado", false)
                    .between("t0.fecha_creacion", firstDayOfMonth, lastDayOfMonth)
                    .eq("equipo.superintendencia.mina.id", minaId)
                    .not(Expr.eq("inspeccion_cerrada_id", null))
                    .eq("tipo", MANTENIMIENTO_CORRECTIVO).findRowCount();

            mesesSeleccionados.add(mes);
            inspeccionesAbiertaXMes.add(cantInspeccionesAbiertas);
            inspeccionesCerradasXMes.add(cantInspeccionesCerradas);
        });
        map.put("meses", Lists.reverse(mesesSeleccionados));
        map.put("series", series);
        map.put("inspecciones_abiertas", Lists.reverse(inspeccionesAbiertaXMes));
        map.put("inspecciones_cerradas", Lists.reverse(inspeccionesCerradasXMes));
        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Fur getFur() {
        return fur;
    }

    public void setFur(Fur fur) {
        this.fur = fur;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public String getResumenActividad() {
        return resumenActividad;
    }

    public void setResumenActividad(String resumenActividad) {
        this.resumenActividad = resumenActividad;
    }

    public boolean isAprobadoSoporte() {
        return aprobadoSoporte;
    }

    public void setAprobadoSoporte(boolean aprobadoSoporte) {
        this.aprobadoSoporte = aprobadoSoporte;
    }

    public boolean isCompartido() {
        return compartido;
    }

    public void setCompartido(boolean compartido) {
        this.compartido = compartido;
    }

    public Long getHorometro() {
        return horometro;
    }

    public void setHorometro(Long horometro) {
        this.horometro = horometro;
    }

    public Inspeccion getInspeccionCerrada() {
        return inspeccionCerrada;
    }

    public void setInspeccionCerrada(Inspeccion inspeccionCerrada) {
        this.inspeccionCerrada = inspeccionCerrada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Inspeccion{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", equipo=" + equipo +
                ", fur=" + fur +
                ", ordenTrabajo='" + ordenTrabajo + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", responsable=" + responsable +
                ", resumenActividad='" + resumenActividad + '\'' +
                ", aprobadoSoporte=" + aprobadoSoporte +
                ", compartido=" + compartido +
                ", horometro=" + horometro +
                ", inspeccionCerrada=" + inspeccionCerrada +
                '}';
    }
}
