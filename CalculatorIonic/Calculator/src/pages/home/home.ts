import { Component } from '@angular/core';
import { NavController, NavParams, Events } from 'ionic-angular';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
    number1: string;
    number2: string;
    message: string;
    lastResults: Array<{result: string}>;

    constructor(public navCtrl: NavController, public navParams: NavParams, public events: Events, private storage: Storage) {
      	  
      try{
        this.lastResults = JSON.parse(localStorage.getItem("lastCalculatorResults"));          
      }catch(e){
        this.lastResults = [];           
      }
      if(this.lastResults == null){
          this.lastResults = [];   
      }
      this.message = "";  
    }
  
    add(){
      this.message = "" + (parseFloat(this.number1) + parseFloat(this.number2));
      if(this.message == "NaN"){
          this.message = "";
      }
      this.addResult();
    }

    minus(){
      this.message = "" + (parseFloat(this.number1) - parseFloat(this.number2));
      if(this.message == "NaN"){
          this.message = "";
      }
      this.addResult();
    }
    
    divide(){
      this.message = "" + (parseFloat(this.number1) / parseFloat(this.number2));
      if(this.message == "NaN"){
          this.message = "";
      }
      this.addResult();
    }
    
    multiple(){
      this.message = "" + (parseFloat(this.number1) * parseFloat(this.number2));
      if(this.message == "NaN"){
          this.message = "";
      }
      this.addResult();
    }
    
    addResult(){        
        if(this.lastResults.length >= 10){
            this.lastResults.shift();
        }                
        this.lastResults.push({
        result: this.message+" "
        });
        this.saveList();
    }
    
    saveList(){        
      localStorage.clear();
      localStorage.setItem("lastCalculatorResults", JSON.stringify(this.lastResults));
    }
}
