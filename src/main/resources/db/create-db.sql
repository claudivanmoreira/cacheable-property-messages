/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Claudivan Moreira
 * Created: 01/04/2017
 */
CREATE TABLE TB_CONFIG_PROPERTY (
    KEY varchar (100),
    VALUE varchar (100),
    DESCRIPTION varchar (100),
    CONFIG_NAME varchar (100),
    PRIMARY KEY(KEY, CONFIG_NAME)
);

