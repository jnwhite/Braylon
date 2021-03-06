package com.dev10.braylon.controllers;

import com.dev10.braylon.models.Bill;
import com.dev10.braylon.models.Customer;
import com.dev10.braylon.models.Product;
import com.dev10.braylon.models.Role;
import com.dev10.braylon.models.SalesVisit;
import com.dev10.braylon.models.User;
import com.dev10.braylon.service.customerService;
import com.dev10.braylon.service.billService;
import com.dev10.braylon.service.productService;
import com.dev10.braylon.service.salesVisitService;
import com.dev10.braylon.service.userService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public abstract class MainController {

    public static User currentUser;

    @Autowired
    private customerService cServ;

    @Autowired
    private billService bServ;

    @Autowired
    private productService pServ;

    @Autowired
    private salesVisitService svServ;

    @Autowired
    private userService uServ;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        if (userIsAdmin()) {
            List<Bill> allBills = bServ.findAllBills();
            List<SalesVisit> allSalesVisits = svServ.findAllSalesVisits();
            model.addAttribute("orders", allBills);
            model.addAttribute("salesVisits", allSalesVisits);
            return "home";
        } else {
            List<SalesVisit> visits = uServ.findAllSalesVisitsByUsername(currentUser.getUsername());
            List<Bill> bills = bServ.findAllBillsByUsername(currentUser.getUsername());
            model.addAttribute("orders", bills);
            model.addAttribute("salesVisits", visits);
            return "home";
        }
    }

    @GetMapping("/customer")
    public String loadCustomerView(Model model) {
        List<Customer> custList;
        if (userIsAdmin()) {
            custList = cServ.findAllCustomers();
        } else {
            custList = cServ.findAllCustomersByUsername(currentUser.getUsername());
        }
        model.addAttribute("customers", custList);
        return "customer";
    }

    //Adding Customer
    @GetMapping("/addCustomer/{username}")
    public String loadAddingCustomer(@PathVariable String username, Model model) {
        if(userIsAdmin()) {
            model.addAttribute("users", uServ.findAllSalesReps());
        }
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("customer", customer);
        model.addAttribute("header", "add");
        return "customerDetail";
    }

    @PostMapping("/addCustomer/{username}")
    public String addNewCustomer(@PathVariable String username, Customer customer) {
        cServ.addCustomer(customer);
        return "redirect:/home";
    }

    //Editing Customer
    @GetMapping("/editcustomer/{customerId}")
    public String loadEditCustomer(Model model, @PathVariable int customerId) {
        Customer customer = cServ.findCustomerById(customerId);
        if (userIsAdmin()) {
            model.addAttribute("users", uServ.findAllSalesReps());
        }
        model.addAttribute("customer", customer);
        model.addAttribute("header", "edit");
        return "customerDetail";
    }

    @PostMapping("/editcustomer/{customerId}")
    public String editCustomer(@PathVariable int customerId, Customer customer) {
        cServ.editCustomer(customer);
        return "redirect:/home";
    }

    //Adding SalesRep
    @GetMapping("/addSalesRep")
    public String loadAddingSalesRep(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("header", "Add");
        return "salesRepDetail";
    }

    @PostMapping("/addSalesRep")
    public String addNewSalesRep(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        uServ.addUser(user);
        return "redirect:/home";
    }

    //Editing SalesRep
    @GetMapping("/salesRep/{username}")
    public String loadEditSalesRep(Model model, @PathVariable String username) {
        User user = uServ.findUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("header", "Edit");
        return "salesRepDetail";
    }

    @PostMapping("/salesRep/{username}")
    public String editSalesRep(@PathVariable String username, User user) {
        uServ.editUser(user);
        return "redirect:/home";
    }

    //Adding SalesVisit
    @GetMapping("/addSalesVisit")
    public String viewAddSalesVisit(Model model) {
        List<Customer> customers = cServ.findAllCustomersByUsername(currentUser.getUsername());
        model.addAttribute("customers", customers);
        return "salesVisitDetail";
    }

    @PostMapping("/addSalesVisit")
    public String processAddSalesVisit(SalesVisit visit) {
        svServ.addSalesVisit(visit);
        return "redirect:/home";
    }

    //Adding Bill
    @GetMapping("/addOrder/{username}")
    public String viewAddBill(@PathVariable String username, Model model, Bill bill) {
        List<Customer> customers = cServ.findAllCustomersByUsername(currentUser.getUsername());
        model.addAttribute("customers", customers);
        List<Product> products = pServ.findAllProducts();
        model.addAttribute("products", products);
        return "orderAdd";
    }

    @PostMapping("/addOrder/{username}")
    public String processAddBill(@PathVariable String username, Bill bill) {
        
        bServ.addBill(bill);
        return "redirect:/home";
    }
    
    //Editing Bill
    @GetMapping("/editOrder/{username}/{billId}")
    public String viewEditBill(@PathVariable String username, @PathVariable int billId, Model model, Bill bill) {
        Bill myBill = bServ.findBillByBillId(billId);
        List<Customer> customers = cServ.findAllCustomersByUsername(currentUser.getUsername());
        model.addAttribute("bill", myBill);
        model.addAttribute("customers", customers);
        List<Product> products = pServ.findAllProducts();
        model.addAttribute("products", products);
        return "orderDetail";
    }

    @PostMapping("/editOrder/{username}")
    public String processEditBill(@PathVariable String username, Bill bill) {
        bServ.addBill(bill);
        return "redirect:/home";
    }

    private boolean userIsAdmin() {
        List<Role> userRoles = currentUser.getRoles();
        boolean isAdmin = false;
        for(Role r : userRoles) {
            if(r.getRole().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    // new stuff 2/14/2020 11:30a.m.
    @GetMapping("/salesRepProfile/{username}")
    public String loadSalesRepProfile(Model model, @PathVariable String username) {
        User user = uServ.findUserByUsername(username);
        model.addAttribute("user", user);
        return "salesRepProfile";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/salesRep/")
    public String allSalesReps(Model model) {
        List<User> allSalesReps = uServ.findAllSalesReps();
        model.addAttribute("users", allSalesReps);
        return "salesRep";
    }

    @GetMapping("/")
    public String loadBasePage(Model model) {
        return "redirect:/home";
    }
}
