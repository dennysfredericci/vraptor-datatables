package br.com.fredericci.datatables.converter;

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

		Integer start = Integer.valueOf(this.request.getParameter("iDisplayStart"));
		Integer size = Integer.valueOf(this.request.getParameter("iDisplayLength"));
		String search = this.request.getParameter("sSearch");

		PageRequest pageRequest = new PageRequest();

		pageRequest.setStart(start);
		pageRequest.setSize(size);
		pageRequest.setSearch(search);

		return pageRequest;
	}
}
