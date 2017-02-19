package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import domain.Invoice;

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
	public Invoice create() {
		Invoice res;
		res = new Invoice();
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
		Invoice res = invoiceRepository.save(invoice);
		return res;
	}
	
	
	//Utilites methods
	
}

