package br.com.fredericci.datatables.view;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.fredericci.datatables.Page;
import br.com.fredericci.datatables.converter.DataTable;

@Component
public class PagingResults implements PagingResult {

	private Result result;

	public PagingResults(Result result) {
		this.result = result;
	}

	@Override
	public void from(Page<?> page) {
		DataTable dataTable = new DataTable(page);
		this.result.use(json()).withoutRoot().from(dataTable).include("aaData").serialize();
	}

	public static Class<PagingResults> dataTablesPaging() {
		return PagingResults.class;
	}

}
