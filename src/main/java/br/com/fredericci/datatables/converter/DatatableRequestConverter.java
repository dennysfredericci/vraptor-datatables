package br.com.fredericci.datatables.converter;

import static com.google.common.base.Objects.firstNonNull;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.fredericci.datatables.PageRequest;

@Convert(PageRequest.class)
@RequestScoped
public class DatatableRequestConverter implements Converter<PageRequest> {

	private final HttpServletRequest request;

	public DatatableRequestConverter(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public PageRequest convert(String value, Class<? extends PageRequest> type, ResourceBundle bundle) {

		String iDisplayStart = this.request.getParameter("iDisplayStart");
		String iDisplayLength = this.request.getParameter("iDisplayLength");

		Integer start = Integer.valueOf(firstNonNull(iDisplayStart, "0"));
		Integer size = Integer.valueOf(firstNonNull(iDisplayLength, "10"));
		String search = this.request.getParameter("sSearch");

		PageRequest pageRequest = new PageRequest();

		pageRequest.setStart(start);
		pageRequest.setSize(size);
		pageRequest.setSearch(search);

		return pageRequest;
	}
}
