package br.com.fredericci.datatables.view;

import static br.com.caelum.vraptor.view.Results.representation;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.HTMLSerialization;
import br.com.fredericci.datatables.Page;
import br.com.fredericci.datatables.converter.DataTable;

@Component
public class PagingResults implements PagingResult {

	private Result result;
	private FormatResolver formatResolver;
	private HTMLSerialization html;

	public PagingResults(Result result, FormatResolver formatResolver, HTMLSerialization html) {
		this.result = result;
		this.formatResolver = formatResolver;
		this.html = html;
	}

	@Override
	public void from(Page<?> page) {

		if (html.accepts(formatResolver.getAcceptFormat())) {
			html.from(page);
		} else {
			DataTable dataTable = new DataTable(page);
			this.result.use(representation()).from(dataTable).include("aaData").serialize();
		}

	}

	public static Class<PagingResults> dataTablesPaging() {
		return PagingResults.class;
	}

}
