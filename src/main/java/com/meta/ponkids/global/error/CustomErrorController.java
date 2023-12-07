package com.meta.ponkids.global.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    
    // view default path setting
    private String DEFAULT_ADMIN_VIEW_PATH = "/admin/error/";
    private String DEFAULT_VIEW_PATH = "/error/";
    
    @RequestMapping( value = "/error" )
    public String handleError( HttpServletRequest request, Model model ) {
        Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE );
        
        if ( status != null ) {
            int statusCode = Integer.valueOf( status.toString() );
            model.addAttribute( "statusCode", statusCode );
            
            if ( statusCode == HttpStatus.BAD_REQUEST.value() ) {
                // 400 error
                return DEFAULT_VIEW_PATH + "400";
                
            } else if ( statusCode == HttpStatus.UNAUTHORIZED.value() ) {
                // 401 error
                return DEFAULT_VIEW_PATH + "401";
                
            } else if ( statusCode == HttpStatus.NOT_FOUND.value() ) {
                // 404 error
                return DEFAULT_VIEW_PATH + "404";
                
            } else if ( statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() ) {
                // 500 error
                return DEFAULT_VIEW_PATH + "500";
                
            } else {
                // 그 외 모든 error
                return DEFAULT_VIEW_PATH + "error";
                
            }
        }
        
        // default ERROR 페이지
        return DEFAULT_VIEW_PATH + "error";
    }
    
    @RequestMapping( value = "/error/admin/{statusCode}" )
    public String handleErrorDeliverCode( HttpServletRequest request, 
    		@PathVariable int statusCode,
    		Model model) {
    	Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE );
    	
    		model.addAttribute( "statusCode", statusCode );
    		
    		if ( statusCode == HttpStatus.BAD_REQUEST.value() ) {
    			// 400 error
    			return DEFAULT_ADMIN_VIEW_PATH + "400";
    			
    		} else if ( statusCode == HttpStatus.UNAUTHORIZED.value() ) {
    			// 401 error
    			return DEFAULT_ADMIN_VIEW_PATH + "401";
    			
    		} else if ( statusCode == HttpStatus.NOT_FOUND.value() ) {
    			// 404 error
    			return DEFAULT_ADMIN_VIEW_PATH + "404";
    			
    		} else if ( statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() ) {
    			// 500 error
    			return DEFAULT_ADMIN_VIEW_PATH + "500";
    			
    		} else {
    			// 그 외 모든 error
    			return DEFAULT_ADMIN_VIEW_PATH + "error";
    			
    		}
    	
    }
    
}