
package controllers.tenant;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import services.RequestService;
import controllers.AbstractController;
import domain.Invoice;
import domain.Request;
import domain.Status;

@Controller
@RequestMapping("/invoice/tenant")
public class InvoiceTenantController extends AbstractController {

	@Autowired
	private InvoiceService	invoiceService;
	@Autowired
	private RequestService requestService;

	public InvoiceTenantController() {
		super();
	}

	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public ModelAndView invoice(@RequestParam int requestId) {
		ModelAndView result;
		Collection<Request> requests= requestService.findMyRequest();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/tenant/list.do");
		result.addObject("req", requests);
		
		Request request = requestService.findOne(requestId);
		
		int years=(request.getCreditCard().getExpirationYear()+2000)-1970;
		int month=request.getCreditCard().getExpirationMonth();
		long exp = years*31540000000l+month*2628000000l;
		long finale = exp-Calendar.getInstance().getTime().getTime();
		
		if(request.getInvoice()==null){
			if(request.getStatus().equals(Status.ACCEPTED)){
				Invoice invoice = invoiceService.create(request);
				if(finale>0){
					invoiceService.save(invoice);
					result.addObject("message", "request.invoice.commit.success");
				}else{
					result.addObject("message","request.error.credit");
				}
			}else{
				result.addObject("message", "request.invoice.error.status");
			}
		}else{
			result.addObject("message", "request.invoice.commit.error");
		}
		
		

		

		return result;
	}
}