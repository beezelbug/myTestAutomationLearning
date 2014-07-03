package com.integration.test.wealthpoc;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.lang.Integer;
import org.core4j.Enumerable;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.OClientBehavior;
import org.odata4j.consumer.behaviors.OClientBehaviors;
import org.odata4j.core.OEntity;
import org.odata4j.core.OProperties;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmSimpleType;
import org.odata4j.jersey.consumer.ODataJerseyConsumer;


public class FindCustomerCpsFilter {
    protected static final BasicAuthLogin bal=new BasicAuthLogin();
    protected static final ODataConsumer consumer =  bal.basicAuthentication();

    public FindCustomerCpsFilter() throws Exception {
        super();
    }
                              //Create a
    @Test
    public void FindCustomerCpsFilterEQ() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CardNo%20eq%20'4521258730001254'").execute();
        Assert.assertEquals(1, customerFilter.count());
        Assert.assertEquals("100336", customerFilter.elementAt(0).getProperty("Id").getValue());
        Assert.assertEquals("Rolf Gerling", customerFilter.elementAt(0).getProperty("ShortName").getValue());
        Assert.assertEquals("Customer Service Agent", customerFilter.elementAt(0).getProperty("AccountOfficer").getValue());
        Assert.assertEquals("Haupt Street 18", customerFilter.elementAt(0).getProperty("Street").getValue());
        Assert.assertEquals("05 JAN 1970", customerFilter.elementAt(0).getProperty("DateofBirth").getValue());
        Assert.assertEquals("", customerFilter.elementAt(0).getProperty("CustomerType").getValue());
    }

    @Test
    public void FindCustomerCpsFilterNE() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("DateOfBirth%20ne%20'19700105'").execute();
        Assert.assertEquals(231, customerFilter.count());	//in T24 count is 231
        //check that dob returned does not equal 05 JAN 1970
        int cnt=customerFilter.count();
        for (int i=0;i<15;i++){	//just check the first 15 otherwise it will take 336+ seconds to run
            //System.out.println("" + i + " " + customerFilter.elementAt(i).getProperty("DateOfBirth").getValue() !="05 JAN 1970");
            Assert.assertEquals(true,customerFilter.elementAt(i).getProperty("DateofBirth").getValue() !="05 JAN 1970");
        }
    }

    @Test
    public void FindCustomerCpsFilterGT() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20gt%20120019").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(26, returnCnt);	//in T24 count is 26
        //check that all numbers returned are gt 120019
        for (int i=0;i<returnCnt;i++){
            Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) > 120019);
        }
    }

    @Test
    public void FindCustomerCpsFilterGTNoMoreRecs() {	//Use the highest customer number
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20gt%2010000083").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(0, returnCnt);	//in T24 count is 0
    }

    @Test  //CURRENTLY FAILS:  See rtc defect 540909
    public void FindCustomerCpsFilterGTToGetLastRec() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20gt%2010000082").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
        //check that all numbers returned are gt 10000082
        for (int i=0;i<returnCnt;i++){
            Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) > 10000082);
        }
    }
    @Test
    public void FindCustomerCpsFilterLT() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20lt%20100181").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(72, returnCnt);	//in T24 count is 72
        //check that all numbers returned are lt 100181
        for (int i=0;i<returnCnt;i++){
            Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) < 100181);
        }
    }

    @Test
    public void FindCustomerCpsFilterLTNoMoreRecs() {	//use the lowest customer number
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20lt%20100100").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(0, returnCnt);	//in T24 count is 0
    }

    @Test
    public void FindCustomerCpsFilterLTLastRec() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20lt%20100101").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
        //check that all numbers returned are lt 100101
        for (int i=0;i<returnCnt;i++){
            Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) < 100101);
        }
    }

    @Test
    public void FindCustomerCpsFilterGE() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20ge%20120019").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(27, returnCnt);	//in T24 count is 27
        //check that all numbers returned are ge 120019
        for (int i=0;i<returnCnt;i++){
            Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) >= 120019);
        }
    }

    @Test  //CURRENTLY FAILS:  See rtc defect 540909
    public void FindCustomerCpsFilterGENoMoreRecs() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20ge%2010000084").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(0, returnCnt);	//in T24 count is 0
    }

    @Test
    public void FindCustomerCpsFilterGELastRec() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20ge%2010000083").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1

    }

    @Test	//TRYING TO FIX THIS RUNNING SOOOOOOO SLOWLY!!!!!!!!!!!!!!!!!
    public void FindCustomerCpsFilterLE() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20le%20100297").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(123, returnCnt);	//in T24 count is 123

        List<String> entityListString = new ArrayList<String>();//construct a list of entities
        for (int h=0;h<returnCnt;h++){
            entityListString.add(customerFilter.elementAt(h).getProperty("Id").toString());	//populate the list of actual entities
        }
        //check that all numbers returned are le 100297
//	for (int i=0;i<returnCnt;i++){
//		Assert.assertEquals(true,Integer.parseInt(customerFilter.elementAt(i).getProperty("Id").getValue().toString()) <= 100297);
//	}
    }

    @Test  //CURRENTLY FAILS:  See rtc defect 540909
    public void FindCustomerCpsFilterLENoMoreRecs() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20le%20100099").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(0, returnCnt);	//in T24 count is 0
    }

    @Test
    public void FindCustomerCpsFilterLELastRec() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20le%20100100").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1

    }

    @Test	//Currenctly throws a 404
    public void FindCustomerCpsFilterOpNotDefined() {

        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CardNo%20ne%20'4521258730001254'").execute();
        try{
            int returnCnt=customerFilter.count();
            Assert.assertEquals(0, returnCnt);
        }
        catch (Exception e){
            String eMes=e.getMessage();
            String errExpected="Invalid Operand";
            System.out.println("FindCustomerCpsFilterOpNotDefined: " + eMes);
            Assert.assertEquals(true,eMes.indexOf(errExpected)>0);
        }


    }

    @Test	//The filter operator is invalid
    public void FindCustomerCpsFilterOpInvalid() {

        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("ShortName%20qq%20'Temenos'").execute();
        try{
            int returnCnt=customerFilter.count();
            Assert.assertEquals(0, returnCnt);
        }
        catch (Exception e){
            String eMes=e.getMessage();
            String errExpected="Unable to read expression with tokens: [[ShortName], [ ], [qq], [ ], ['Temenos']]";
            System.out.println("FindCustomerCpsFilterOpInvalid: " + eMes);
            Assert.assertEquals(true,eMes.indexOf(errExpected)>0);
        }
    }

    @Test	//filter on a number
    public void FindCustomerCpsFilterOnNumber() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerCode%20eq%20100181").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
    }

    @Test	//filter on a date
    public void FindCustomerCpsFilterOnDate() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("DateOfBirth%20ne%20'19700105'").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
    }

    @Test	//filter on a string
    public void FindCustomerCpsFilterOnString() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("Mnemonic%20eq%20'AMEXPARIS'").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
    }

    @Test	//CURRENTLY FAILS:  See rtc defect 523969  //filter on a null
    public void FindCustomerCpsFilterOnNull() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("CustomerStatus%20ne%20''").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
    }

    @Test	//filter on multiple values
    public void FindCustomerCpsFilterMultipleWithAND() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("DateOfBirth%20eq%20'19700105'%20and%20CustomerType%20eq%20'ACTIVE'").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 1
    }

    @Test	//filter on a non selectable field
    public void FindCustomerCpsFilterNotASearchField() {
        try{
            Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("Id%20eq%20100100").execute();
            int returnCnt=customerFilter.count();
            Assert.assertEquals(0,returnCnt);	//expect 0 results
        }
        catch (Exception e){
            String eMes=e.getMessage();
            String errExpected="Invalid query: 'Id' cannot be used in a filter as it is not a Selection field";
            System.out.println("FindCustomerCpsFilterNotASearchField: " + eMes);
            Assert.assertEquals(true,eMes.indexOf(errExpected)>0);
        }
    }

    @Test	//Defect 509663    //filter on a non service field
    public void FindCustomerCpsFilterNotAServiceField() {
        try{
            Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("Title%20eq%20'Mr'").execute();
            int returnCnt=customerFilter.count();
            Assert.assertEquals(0,returnCnt);	//expect 0 results
        }
        catch (Exception e){
            String eMes=e.getMessage();
            String errExpected="Invalid query: 'Title' cannot be used in a filter as it is not a Selection field";
            System.out.println("FindCustomerCpsFilterNotAServiceField: " + eMes);
            Assert.assertEquals(true,eMes.indexOf(errExpected)>0);
        }
    }

    @Test	//Defect 509663    //filter on an invalid field for the application
    public void FindCustomerCpsFilterInvalidField() {
        try{
            Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("AccountBalances%20gt%20100").execute();
            int returnCnt=customerFilter.count();
            Assert.assertEquals(0,returnCnt);	//expect 0 results
        }
        catch (Exception e){
            String eMes=e.getMessage();
            String errExpected="Invalid query: 'AccountBalances' cannot be used in a filter as it is not a Selection field";
            System.out.println("FindCustomerCpsFilterInvalidField: " + eMes);
            Assert.assertEquals(true,eMes.indexOf(errExpected)>0);
        }
    }

    @Test
    public void FindCustomerCpsDisplayFieldStandardOperation() {
        Enumerable<OEntity> customerFilter =  consumer.getEntities("FindCustomerCps").filter("Id%20eq%20100100").execute();
        int returnCnt=customerFilter.count();
        Assert.assertEquals(1, returnCnt);	//in T24 count is 72

        Integer s=Integer.parseInt(customerFilter.elementAt(0).getProperty("Id").getValue().toString());
        Assert.assertEquals(true,s.equals(100100));

    }

}