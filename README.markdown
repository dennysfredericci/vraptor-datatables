## vraptor-datatables

Integração fácil com o datatables.net

# Instalando

Faça o download do jar e adicione no seu classpath

Para o plugin funcionar corretamente será necessário adicionar o iogi no seu classpath e declarar no web.xml

	<context-param>
		<param-name>br.com.caelum.vraptor.packages</param-name>
		<param-value>br.com.caelum.vraptor.util.hibernate,br.com.caelum.vraptor.http.iogi</param-value>
	</context-param>

# Como usar

View:
	

	<html>
	
		...
		...
		
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="tableUsuarios">
			<thead>
				<tr>
					<th style="display: none;">Id</th>
					<th>Nome</th>
					<th>E-mail</th>
				</tr>
			</thead>	
		</table>

		<script type="text/javascript">

			$(document).ready(function(){

				$("#tableUsuarios").dataTable({

					"aoColumns" : [
					               			{ "sTitle": "Id", "mDataProp": "id", "bSortable" : false, "bVisible": false },
					               			{ "sTitle": "Nome", "mDataProp": "nome", "bSortable" : false },
					               			{ "sTitle": "E-mail", "mDataProp": "email", "bSortable" : false }
					              ],


					"bServerSide": true,
					"sAjaxSource": '/usuarios/paginar'
				});
			});

		</script>
		
		...
		...
		
	</html>


Controller:

	import static br.com.fredericci.datatables.view.PagingResults.dataTablesPaging;
	import ...
	import ...

	@Resource
	public class UsuarioController {

		private final Result result;
		private final UsuarioRepository repository;

		public UsuarioController(Result result, UsuarioRepository repository) {
			this.result = result;
			this.repository = repository;
		}


		@Get("/usuarios/paginar")
		public void paginate(PageRequest pageRequest) {
			Page<Usuario> paginaDeUsuarios = this.repository.paginate(pageRequest);
			this.result.use(dataTablesPaging()).from(paginaDeUsuarios);
		}
		
	}
	

Repository:

	import ...
	import ...

	@Component
	public class UsuarioRepository {

		public UsuarioRepositoryImpl(Session session) {
			super(session);
		}

		@Override
		public Page<Usuario> paginate(PageRequest pageRequest) {

			String nome = pageRequest.getSearch() == null ? "" : pageRequest.getSearch();

			Criteria criteria = session.createCriteria(Usuario.class);

			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));

			criteria.setFirstResult(pageRequest.getStart());
			criteria.setMaxResults(pageRequest.getSize());

			List<Usuario> resultList = criteria.list();
			Page<Usuario> page = new PageResponse<Usuario>(resultList, pageRequest, getRowCount());

			return page;
		}

		private Long getRowCount() {

			Criteria criteria = session.createCriteria(clazz.getName());
			criteria.setProjection(Projections.rowCount());
			Long rownCount = (Long) criteria.uniqueResult();

			if (rownCount == null) {
				rownCount = 0L;
			}

			return rownCount;
		}

}


# Onde obter ajuda

Você pode obter ajuda dos desenvolvedores do vraptor na lista de discussão

http://groups.google.com/group/caelum-vraptor

