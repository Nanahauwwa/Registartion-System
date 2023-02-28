package io.nana.Registration.registeration;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
//@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;


    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allstudlist", registrationServiceImpl.getAllStudent());
        return "index";
    }

    @GetMapping("/record")
    public String viewRecordPage(Model model) {
        model.addAttribute("allstudlist", registrationServiceImpl.getAllStudent());
        return "record";
    }

    @GetMapping("/addnew")
    public String addNewStudent(Model model) {
        RegistrationModel registrationModel = new RegistrationModel();
        model.addAttribute("student", registrationModel);
        return "newstudent";

    }

    //@PostMapping("/save")
    //public String saveStudent(@ModelAttribute("student") RegistrationDTO registrationDTO) {
      //  registrationServiceImpl.save(registrationDTO);

      //  return "redirect:/";
    //}
      @PostMapping("/save")
      public String saveStudent(@ModelAttribute("student") RegistrationDTO registrationDTO, Model model) {
//          String email = ;
//          RegistrationModel existingStudent = registrationServiceImpl.getByEmail(email);

          if (registrationServiceImpl.emailExist(registrationDTO.getEmail())) {
              // email already exists, display error message
//              System.out.println("OOOpppps Email exists already perhaps use ur grandmother email");
//              model.addAttribute("errorMessage", "Email address already exists");
              return "emailexist";
          } else {
              // email does not exist, save new student
              registrationServiceImpl.save(registrationDTO);
              return "redirect:/";
          }
      }

    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        RegistrationModel registrationModel = registrationServiceImpl.getById(id);
        model.addAttribute("student", registrationModel);
        return "update";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
        registrationServiceImpl.deleteViaId(id);
        return "redirect:/";
    }
    @GetMapping("/table/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<RegistrationModel> data = registrationServiceImpl.getAlldata();

        //Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("My Table Data");

        //Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("First Name");
        headerRow.createCell(1).setCellValue("Middle Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("DOB");
        headerRow.createCell(5).setCellValue("Gender");
        headerRow.createCell(6).setCellValue("Postal Code");
        headerRow.createCell(7).setCellValue("Street Address");
        headerRow.createCell(8).setCellValue("City");
        headerRow.createCell(9).setCellValue("State");
        headerRow.createCell(10).setCellValue("Phone No");
        headerRow.createCell(11).setCellValue("Course");
        // ... add more columns as needed

        // Populate data rows
        int rowNum = 1;
        for (RegistrationModel rowData : data) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(rowData.getFirstname());
            dataRow.createCell(1).setCellValue(rowData.getMiddlename());
            dataRow.createCell(2).setCellValue(rowData.getLastname());
            dataRow.createCell(3).setCellValue(rowData.getEmail());
            dataRow.createCell(4).setCellValue(rowData.getDob());
            dataRow.createCell(5).setCellValue(rowData.getGender());
            dataRow.createCell(6).setCellValue(rowData.getPostalCode());
            dataRow.createCell(7).setCellValue(rowData.getStreetAddress());
            dataRow.createCell(8).setCellValue(rowData.getCity());
            dataRow.createCell(9).setCellValue(rowData.getState());
            dataRow.createCell(10).setCellValue(rowData.getPhoneNo());
            dataRow.createCell(11).setCellValue(rowData.getCourse());
            // ... add more columns as needed
        }

        // Set response headers for Excel file download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=my-table-data.xlsx");

        // Write Excel workbook to response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }






    /*@PostMapping("/create")
    public String notifyUser(@RequestParam String firstname, @RequestParam String email, RegistrationModel registrationModel) {

        return "redirect:/login";
    }*/

}



    

