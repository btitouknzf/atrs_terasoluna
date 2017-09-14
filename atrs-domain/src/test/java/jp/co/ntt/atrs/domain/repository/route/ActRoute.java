package jp.co.ntt.atrs.domain.repository.route;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jp.co.ntt.atrs.domain.model.Airport;

/*
 * DBへセットアップ後の検証用model
 */
public class ActRoute implements Serializable {
	/**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 4868076356251733173L;

    /**
     * 区間番号。
     */
    private Integer routeNo;

    /**
     * 基本料金。
     */
    private Integer basicFare;

    /**
     * 到着空港。
     */
    private Airport arrivalAirport;

    /**
     * 出発空港。
     */
    private Airport departureAirport;

    
    /* 追加 */
    private String flightTime;
    
    
    /**
     * 区間番号を取得する。
     * 
     * @return 区間番号
     */
    public Integer getRouteNo() {
        return routeNo;
    }

    /**
     * 区間番号を設定する。
     * 
     * @param routeNo 区間番号
     */
    public void setRouteNo(Integer routeNo) {
        this.routeNo = routeNo;
    }

    /**
     * 基本料金を取得する。
     * 
     * @return 基本料金
     */
    public Integer getBasicFare() {
        return basicFare;
    }

    /**
     * 基本料金を設定する。
     * 
     * @param basicFare 基本料金
     */
    public void setBasicFare(Integer basicFare) {
        this.basicFare = basicFare;
    }

    /**
     * 到着空港を取得する。
     * 
     * @return 到着空港
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * 到着空港を設定する。
     * 
     * @param arrivalAirport 到着空港
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * 出発空港を取得する。
     * 
     * @return 出発空港
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * 出発空港を設定する。
     * 
     * @param departureAirport 出発空港
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }
    
    /* 追加 */
	public String getFlightTime() {
		return flightTime;
	}
	/* 追加 */
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


}
