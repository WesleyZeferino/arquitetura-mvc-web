package br.com.arquitetura.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import br.com.arquitetura.objeto.Objeto;

public final class UtilReports extends Objeto {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante MIME_TYPE_EXECEL. */
	private static final String MIME_TYPE_XLS = "application/vnd.ms-excel";

	/** Constante MIME_TYPE_PDF. */
	private static final String MIME_TYPE_PDF = "application/pdf";

	/** Constante PATH_SEPARATOR. */
	private static final String PATH_SEPARATOR = File.separator;

	/** Constante DIRETORIO_RELATORIOS. */
	private static final String DIRETORIO_RELATORIOS = PATH_SEPARATOR + "WEB-INF" + PATH_SEPARATOR + "relatorios" + PATH_SEPARATOR;

	private UtilReports() {
		super();
	}

	public static void gerarRelatorio(final Collection<? extends Objeto> dataSource, final Map<String, Object> parametros, final String jasperFileName, final FacesContext facesContext) throws IOException, JRException {
		gerarRelatorioPDF(dataSource, parametros, jasperFileName, facesContext, Boolean.FALSE);
	}

	public static void gerarRelatorioPagina(final Collection<? extends Objeto> dataSource, final Map<String, Object> parametros, final String jasperFileName, final FacesContext facesContext) throws IOException, JRException {
		gerarRelatorioPDF(dataSource, parametros, jasperFileName, facesContext, Boolean.TRUE);

	}

	public static void gerarRelatorioPDF(final Collection<? extends Objeto> dataSource, final Map<String, Object> parametros, final String jasperFileName, final FacesContext facesContext, final boolean isPagina) throws IOException, JRException {
		JasperPrint print = null;

		if (isVazio(dataSource)) {
			print = JasperFillManager.fillReport(UtilReports.getRealPath(UtilReports.DIRETORIO_RELATORIOS + jasperFileName, facesContext), parametros, new JREmptyDataSource());
		} else {
			print = JasperFillManager.fillReport(UtilReports.getRealPath(UtilReports.DIRETORIO_RELATORIOS + jasperFileName, facesContext), parametros, new JRBeanCollectionDataSource(dataSource));
		}

		final byte[] arquivo = JasperExportManager.exportReportToPdf(print);
		final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType(UtilReports.MIME_TYPE_PDF);

		if (!isPagina) {
			response.setHeader("Content-disposition", "attachment; filename=\"" + UtilReports.obterNome(jasperFileName) + ".pdf\"");
		}

		response.setContentLength(arquivo.length);

		final ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(arquivo, 0, arquivo.length);
		outputStream.flush();
		outputStream.close();

		FacesContext.getCurrentInstance().responseComplete();
	}

	public static void gerarRelatorioXLS(final List<? extends Object> dataSource, final Map<String, Object> parametros, final String jasperFileName, final FacesContext facesContext) throws IOException, JRException {

		JasperPrint print = null;

		if (isVazio(dataSource)) {
			print = JasperFillManager.fillReport(UtilReports.getRealPath(UtilReports.DIRETORIO_RELATORIOS + jasperFileName, facesContext), parametros, new JREmptyDataSource());
		} else {
			print = JasperFillManager.fillReport(UtilReports.getRealPath(UtilReports.DIRETORIO_RELATORIOS + jasperFileName, facesContext), parametros, new JRBeanCollectionDataSource(dataSource));
		}

		final JRXlsExporter exporterXLS = new JRXlsExporter();
		final ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();

		exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT_LIST, Arrays.asList(print));
		exporterXLS.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
		exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		exporterXLS.exportReport();

		final byte[] arquivo = xlsReport.toByteArray();

		final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType(UtilReports.MIME_TYPE_XLS);
		response.setHeader("Content-disposition", "attachment; filename=\"" + UtilReports.obterNome(jasperFileName) + ".xls\"");
		response.setContentLength(arquivo.length);

		final ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(arquivo, 0, arquivo.length);
		outputStream.flush();
		outputStream.close();

		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * Método responsável por obter o caminho real ao arquivo que será exibido.
	 * @author Wesley Luiz
	 * @param path - <code>String</code> que recebe o nome do arquivo <i>(.jasper)</i>.
	 * @param facesContext - <i>Objeto</i> referente ao contexto corrente da aplicação.
	 * @return Retorna uma <code>String</code> contendo o caminho do relatório.
	 */
	public static String getRealPath(final String path, final FacesContext facesContext) {
		return UtilReports.getContext(facesContext).getRealPath(path);
	}

	private static ServletContext getContext(final FacesContext facesContext) {
		return (ServletContext) facesContext.getExternalContext().getContext();
	}

	public static String obterNome(final String jasperFileName) {
		return jasperFileName.substring(0, jasperFileName.lastIndexOf("."));
	}
}
