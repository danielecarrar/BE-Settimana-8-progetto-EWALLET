package it.contocorrente;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import it.cc.pojo.ContoCorrente;
import it.cc.pojo.Movimenti;

@WebService
@Path("/ewallet")
public class GestioneEwallet {

	private static List<ContoCorrente> listaConti = new ArrayList<>();
	private static List<Movimenti> listaMovimenti = new ArrayList<>();

	// METODI

	// -----------------------creazione nuovo conto - INSERT - OK

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response inserisciConto(ContoCorrente cc) {
		listaConti.add(cc);
		return Response.status(200).entity("Inserimento del conto completato!").build();
	}

//							ESEMPIO
//	 {
//	        "iban": "IT00000",
//	        "data": "20-12-2018",
//	        "saldo": 531.50,
//	        "intestatario": "Luca Giuliani"
//	 }

	// -----------------------recuperare tutti i conti presenti - OK

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContoCorrente> recuperaConti() {
		return listaConti;
	}

	// ----------------------- eliminazione di un conto - OK
//  (funziona cliccando ancora SEND)

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancellaConto(ContoCorrente cc) {
		for (ContoCorrente conto : listaConti) {
			if (conto.getIban().equals(cc.getIban())) {
				listaConti.remove(cc);
			}
		}
		return Response.status(200).entity("Eliminazione del conto completata").build();
	}

	// -----------------------metodo cancellazione con iban passato nell'URI - OK (2x SEND)

	@DELETE
	@Path("/{iban}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancellaContoUri(@PathParam("iban") String iban) {
		for (ContoCorrente conto : listaConti) {
			int index = listaConti.lastIndexOf(conto);
			if (conto.getIban().equals(iban)) {
				listaConti.remove(index);
			}
		}
		return Response.status(200).entity("Eliminazione del conto completata").build();
	}

	// -----------------------recuperare tutti i movimenti presenti - OK
	@GET
	@Path("/{iban}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperaMovimenti(@PathParam("iban") String iban) {
		System.out.println(iban);
		List<Movimenti> listTemp = new ArrayList<Movimenti>();
		for (Movimenti mov : listaMovimenti) {
			System.out.println(mov.getIbanMov());
			if (mov.getIbanMov().equals(iban)) {
				listTemp.add(mov);
				System.out.println(mov.toString());
			}
		}
		return Response.status(200).entity(listTemp).build();
	}

	// -----------------------aggiorna Conto - OK
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response aggiornaConto(ContoCorrente cc) {
		for (ContoCorrente ccc : listaConti) {
			int index = listaConti.lastIndexOf(ccc);
			if (ccc.getIban().equals(cc.getIban())) {
				listaConti.set(index, cc);
			}
		}
		return Response.status(200).entity("Aggiornamento completato!").build();
	}

	// -----------------------crea prelievo - OK

	@POST
	@Path("/preleva/{ibanMov}/{importo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response preleva(@PathParam("ibanMov") String ibanMov, @PathParam("importo") double importo) {

		String tipo = "Prelievo";

		Movimenti movimento = new Movimenti();

		for (ContoCorrente conto : listaConti) {
			if (conto.getIban().equals(ibanMov)) {
				double impor = conto.getSaldo() - importo;
				conto.setSaldo(impor);
				double saldo = conto.getSaldo();

				movimento.setIbanMov(ibanMov);
				movimento.setImporto(importo);
				movimento.setTipo(tipo);
				movimento.setSaldo(saldo);

				listaMovimenti.add(movimento);
				System.out.println(movimento.toString());
				if (!listaConti.contains(conto)) {
					return Response.status(404)
							.entity("Conto corrente non trovato! Controlla che l'IBAN inserito sia corretto").build();
				}
				return Response.status(200).entity("Prelievo completato!").build();
			}
		}
		return Response.status(404).entity("Conto corrente non trovato!").build();
	}

	// -----------------------effettua deposito - OK

	@POST
	@Path("/deposita/{ibanMov}/{importo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deposita(@PathParam("ibanMov") String ibanMov, @PathParam("importo") double importo) {
		String tipo = "Deposito";

		Movimenti movimento = new Movimenti();

		for (ContoCorrente conto : listaConti) {
			if (conto.getIban().equals(ibanMov)) {
				double impor = conto.getSaldo() + importo;
				conto.setSaldo(impor);
				double saldo = conto.getSaldo();

				movimento.setIbanMov(ibanMov);
				movimento.setImporto(importo);
				movimento.setTipo(tipo);
				movimento.setSaldo(saldo);

				listaMovimenti.add(movimento);
				System.out.println(movimento.toString());
				if (!listaConti.contains(conto)) {
					return Response.status(404)
							.entity("Conto corrente non trovato! Controlla che l'IBAN inserito sia corretto").build();
				}

				return Response.status(200).entity("Deposito completato!").build();
			}
		}
		return Response.status(404).entity("Conto corrente non trovato!").build();
	}
}