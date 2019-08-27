package org.launchcode.controllers;

import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.launchcode.models.Job;
import org.launchcode.models.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job aJob = jobData.findById(id);
        model.addAttribute("job", aJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            return "new-job";
        }

        //Temporary block to see what IDs come back to the controller.
//        System.out.println("\n\n\n\nName: " + jobForm.getName());
//        System.out.println("Employer ID: " + jobForm.getEmployerId());
//        System.out.println("Location ID: " + jobForm.getLocationId());
//        System.out.println("Position Type ID: " + jobForm.getPositionId());
//        System.out.println("Core Competency ID: " + jobForm.getCoreCompetencyId() + "\n\n\n\n");


        //Gets the lists of Job information and finds the one with the corresponding ID and puts it into the object
        //to make the aJob object.
          Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
          Location location = jobData.getLocations().findById(jobForm.getLocationId());
          CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
          PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionId());
          String name = jobForm.getName();

          Job job = new Job(name , employer, location, positionType, coreCompetency);
        //  System.out.println(job.getId());
          jobData.add(job);
        return "redirect:?id=" + job.getId();

    }
}
