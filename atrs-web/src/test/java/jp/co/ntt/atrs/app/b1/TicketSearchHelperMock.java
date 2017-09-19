package jp.co.ntt.atrs.app.b1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FlightType;

public class TicketSearchHelperMock extends TicketSearchHelper {
	
	/**
     * デフォルト値を持つ空席照会フォームを作成する。
     * 
     * @return 空席照会フォーム
     */
    public TicketSearchForm createDefaultTicketSearchForm() {
    	
        TicketSearchForm ticketSearchForm = new TicketSearchForm();
        Date mockDate = new Date();
        try{
			//現在時刻
			String strBeginDate = "2017/09/15 00:00:00";
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			mockDate = sdFormat.parse(strBeginDate);
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
        ticketSearchForm.setFlightType(FlightType.RT);
        ticketSearchForm.setDepAirportCd("HND");
        ticketSearchForm.setArrAirportCd("HND");
        ticketSearchForm.setOutwardDate(mockDate);
        ticketSearchForm.setHomewardDate(mockDate);
        ticketSearchForm.setBoardingClassCd(BoardingClassCd.N);
        
        System.out.println(ticketSearchForm);
        
        return ticketSearchForm;
    }
	
	
	/**
     * 空席照会画面(TOP画面)の表示情報を作成する。
     * 
     * @return 空席照会画面(TOP画面)の表示情報
     */
	public FlightSearchOutputDto createFlightSearchOutputDto(){
		FlightSearchOutputDto mockDto = new FlightSearchOutputDto();
		
		try{
			/**
		     * 空席照会可能時期(始)。
		     */
			//現在時刻
			String strBeginDate = "2017/09/15 00:00:00";
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date begindate = sdFormat.parse(strBeginDate);
			mockDto.setBeginningPeriod(begindate);
			
			/**
		     * 空席照会可能時期(終)。
		     */
			//現在日付+90日　固定値
			String strEndDate = "2017/12/14 00:00:00";
			Date ecddate = sdFormat.parse(strEndDate);
			mockDto.setEndingPeriod(ecddate);
			
			/**
		     * 復路予約可能時間間隔(分)。
		     */
			//120 固定値
			mockDto.setReserveIntervalTime(120);
			
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(mockDto);
		return mockDto;
	}
	
}
