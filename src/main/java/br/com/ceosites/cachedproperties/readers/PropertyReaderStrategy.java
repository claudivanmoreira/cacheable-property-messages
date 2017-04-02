package br.com.ceosites.cachedproperties.readers;

import java.util.Map;

import br.com.ceosites.cachedproperties.util.KeysIterable;
import com.google.common.base.Optional;

public interface PropertyReaderStrategy {
	/**
	 * 
	 * @return iterable contendo todas os nomes de chaves configuradas
	 */
	public KeysIterable getKeys();
	/**
	 * 
	 * @param key nome da chave para busca
	 * @return Valor da chave como String, ou uma String vazia ("") caso a chaves nao seja encontrada.
	 */
	public Optional<String> getValue(String key);
	/**
	 * 
	 * @return Map contendo as chaves e seus valores configurados 
	 */
	public Map<String, Optional<String>> getMapKeys();

}
