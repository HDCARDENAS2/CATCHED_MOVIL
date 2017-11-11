package com.catched_movil.app.Control;

import com.catched_movil.app.Model.Usuario;

/**
 * Created by hernandario on 05/11/2017.
 */

public class Constantes {

    public static String HOST                                = "";
    public static String AUTENTIFICACION                     = "";
    public static String CONCATENACION                       = "::";
    public static String ULTIMA_FECHA                        = "";
    public final static int MODO_SERVICIO                    = ServiceHandler.POST;
    public static Usuario o_usuario                          = null;
    public final static String KEY                           = "key";
    public final static String MSN                           = "msn";
    public final static String JSON_RESULTADO                = "resultado";
    public final static String JSON_MENSAJE                  = "messages";
    public final static String JSON_ERROR                    = "errors";
    public final static String FECHA                         = "fecha";

    public final static String URL_BASE                      = "/CATCHED_WEB_APP/PHP/Servicios/";

    public final static String WS_NOTIFICACION               =  URL_BASE+"Notificacion.php";
    public final static String WS_LONGIN                     =  URL_BASE+"LoginUsuario.php";
    public final static String WS_CS_ENVENTOS                =  URL_BASE+"ConsultaEnventos.php";
    public final static String WS_CS_IMAGEN                  =  URL_BASE+"ConsultaImagen.php";
    public final static String WS_REGISTRO_APP               =  URL_BASE+"RegistroApp.php";
    public final static String WS_REGISTRO_US                =  URL_BASE+"RegistroUsuario.php";
    public final static String WS_MODIFICAR_US               =  URL_BASE+"ModificarUsuario.php";

    public final static String CT_EVENTO_COD_EVENTO          = "COD_EVENTO";
    public final static String CT_EVENTO_RUTA                = "RUTA";
    public final static String CT_EVENTO_NOMBRE_IMG          = "NOMBRE_IMG";
    public final static String CT_EVENTO_FECHA               = "FECHA_CREACION";

    public final static String CT_EVENTO[]                   = { CT_EVENTO_COD_EVENTO
                                                                ,CT_EVENTO_RUTA
                                                                ,CT_EVENTO_NOMBRE_IMG
                                                                ,CT_EVENTO_FECHA
                                                               };
    public final static String CT_EVENTO_VISTA[]             = { CT_EVENTO_COD_EVENTO
                                                                ,CT_EVENTO_FECHA
                                                                };


    public final static String CT_USUARIO_COD_USUARIO       = "COD_USUARIO";
    public final static String CT_USUARIO_NOMBRES           = "NOMBRES";
    public final static String CT_USUARIO_USUARIO           = "USUARIO";
    public final static String CT_USUARIO_FECHA_CREACION    = "FECHA_CREACION";
    public final static String CT_USUARIO_COD_ROLL          = "COD_ROLL";
    public final static String CT_USUARIO_IND_ESTADO        = "IND_ESTADO";

    public final static String CT_USUARIO[]                 = { CT_USUARIO_COD_USUARIO
                                                               ,CT_USUARIO_NOMBRES
                                                               ,CT_USUARIO_USUARIO
                                                               ,CT_USUARIO_FECHA_CREACION
                                                               ,CT_USUARIO_COD_ROLL
                                                               ,CT_USUARIO_IND_ESTADO
                                                                };

    public final static String PASSWORD                    = "PASSWORD";
    public final static String PASSWORD_C                  = "PASSWORD_C";

}

