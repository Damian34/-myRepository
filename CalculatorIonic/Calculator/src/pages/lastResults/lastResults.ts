import { Component } from '@angular/core';
import { NavController, NavParams, Events } from 'ionic-angular';

@Component({
  selector: 'page-lastResults',
  templateUrl: 'lastResults.html'
})
export class LastResultsPage {

    lastResults: Array<{result: string}>;
    
    constructor(public navCtrl: NavController, public navParams: NavParams, public events: Events) {      
      try{
        this.lastResults = JSON.parse(localStorage.getItem("lastCalculatorResults")).reverse();          
      }catch(e){
        this.lastResults = [];           
      }
      if(this.lastResults == null){
          this.lastResults = [];   
      }      
    }
  
}
