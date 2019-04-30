package com.udemy.service.interfaces;

import java.io.IOException;
import java.util.Map;

import com.udemy.util.RestResponse;

public interface IFeriadoAñoService {

	RestResponse registrar(String feriadoaño,Map<String, String>  id) throws IOException;

	RestResponse eliminar(Map<String, String> id);
}
