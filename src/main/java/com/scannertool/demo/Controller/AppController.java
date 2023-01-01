package com.scannertool.demo.Controller;


import com.scannertool.demo.Repository.DataRepository;
import com.scannertool.demo.database.Data;
import com.scannertool.demo.database.DatabaseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Controller
public class AppController {


    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/")
    public String homepage(){
        return "home";
    }

    @GetMapping("/home")
    public String printMenu() {
        return "home";
    }

    @GetMapping("/showdatabase")
    public String showDatabase(Model model){
        model.addAttribute("showdata",dataRepository.findAll(Sort.by(Sort.Direction.ASC, "station")));
        return "showdatabase";
    }

    @GetMapping("/addentry")
    public String addEntry(Model model) {
        model.addAttribute("entry",new Data());
        return "addentry";
    }

    @PostMapping("/entry")
    public String saveData(@ModelAttribute("entry") Data data, RedirectAttributes attributes, BindingResult result){
        if(data.getStation().equals("") || data.getMac().equals("")) {
            attributes.addFlashAttribute("added",  "Fields cannot be empty");
            return "redirect:/addentry";
        }
        try{
            if(dataRepository.findByStation(data.getStation()) == null && dataRepository.findByMac(data.getMac()) == null) {
                dataRepository.save(data);
                attributes.addFlashAttribute("added", "Successfully added " + data.getStation());
                return "redirect:/home";
            }
            attributes.addFlashAttribute("added",  "Entry already exists in database");
            return "redirect:/addentry";
        }catch(Exception e) {
            attributes.addFlashAttribute("added",  "Error occurred " + e);
            return "redirect:/addentry";
        }
    }

    @GetMapping("/removeentry")
    public String removeEntry(Model model) {
        model.addAttribute("entry",new Data());
        return "removeentry";
    }

    @PostMapping("/remove")
    public String removeData(@ModelAttribute("entry") Data data ,RedirectAttributes attributes){
        if(data.getStation().equals("") || data.getMac().equals("")) {
            attributes.addFlashAttribute("added",  "Fields cannot be empty");
            return "redirect:/addentry";
        }
        try{
            Data datas = dataRepository.findByStation(data.getStation());
            dataRepository.delete(datas);
            attributes.addFlashAttribute("removed", "Successfully removed " + datas.getStation());
            return "redirect:/home";
        }catch(Exception e) {
            attributes.addFlashAttribute("message",  "Station doesn't exist");
            return "redirect:/removeentry";
        }
    }

    @GetMapping("/findbarcode")
    public String findBarcode(Model model) {
        model.addAttribute("barcode", new Data());
        return "findbarcode";
    }

    @PostMapping("/printbarcode")
    public String printBarcode(@ModelAttribute Data data, RedirectAttributes attributes, Model model) throws Exception {
        Data bar = dataRepository.findByStation(data.getStation());
        if(bar == null) {
            attributes.addFlashAttribute("error","No match found");
            return "redirect:/findbarcode";
        }
        model.addAttribute("data","BT_ADR"+bar.getMac()+".");
        return "barcode";
    }

//    @GetMapping("/export")
//    public String exportData(Model model, RedirectAttributes redirectAttributes) {
//        String path = "";
//         model.addAttribute("databasefile",new DatabaseFile());
//         return "export";
//    }

    @PostMapping("/exportdata")
    public String export(@ModelAttribute DatabaseFile dbfile, Model model, RedirectAttributes redirectAttributes) throws IOException {
            dbfile.exportCSV(dataRepository);
            redirectAttributes.addFlashAttribute("success","File exported to /Downloads/scannerdata.txt");
        return "redirect:/home";
    }

}
