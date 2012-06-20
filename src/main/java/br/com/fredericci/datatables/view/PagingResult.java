package br.com.fredericci.datatables.view;

import br.com.caelum.vraptor.View;
import br.com.fredericci.datatables.Page;

public interface PagingResult extends View {

	public void from(Page<?> page);

}
