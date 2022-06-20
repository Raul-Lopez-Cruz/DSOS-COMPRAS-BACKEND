/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.exceptions;


import ito.dsos.compras.constans.AuthenticationConstans;

/**
 *
 * @author Oscar
 */
public class UnauthorizedException extends CustomException{
     public UnauthorizedException() {
        super(AuthenticationConstans.INVALID_TOKEN_MENSAJE_EXCEPTION);
    }
}
