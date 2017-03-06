package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import security.LoginService;
import security.UserAccount;
import domain.Invoice;
import domain.Request;
import domain.Status;

@Service
@Transactional
public class InvoiceService {

	//Managed repository
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	//Supporting services
	
	//Constructors
	public InvoiceService() {
		super();
	}
	
	//Simple CRUD methods
	public Invoice create(Request request) {
		Invoice res;
		res = new Invoice();
		res.setRequest(request);
		res.setMoment(Calendar.getInstance().getTime());
		res.setVatNumber("ESA12345678C");
		res.setCreditCard(request.getTenant().getCreditCard());
		res.setDetail(request.getProperty().getName());
		
		long time = (request.getCheckOut().getTime()-request.getCheckIn().getTime())/8640000;
		res.setTotalAmount((time+1)*request.getProperty().getRatePerDay());
		
		return res;
	}
	
	public Collection<Invoice> findAll() {
		Collection<Invoice> res = invoiceRepository.findAll();
		return res;
	}

	public Invoice findOne(int invoiceId) {
		Invoice res = invoiceRepository.findOne(invoiceId);
		return res;
	}
	
	public Invoice save(Invoice invoice) {
		Assert.notNull(invoice, "The invoice to save cannot be null.");
		UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(invoice.getRequest().getStatus().equals(Status.ACCEPTED),"The request must be accepted");
		Assert.isTrue(invoice.getRequest().getTenant().getUserAccount().equals(ua),"You must be the owner of the request");
		Invoice res = invoiceRepository.save(invoice);
		return res;
	}
	
	
	//Utilites methods
	
}

