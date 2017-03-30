package br.com.ceosites.cachedproperties.cache;

public class CacheConstants {
	
	/**
	 * Valor default da quantidade de itens no cache
	 */
	public static Long DEFAULT_CACHE_SIZE = 200L;
	/**
	 * Valor default do tempo que uma chave pode permancer no cache apos sua criacao ou desde a sua ultima atualizacao
	 */
	public static Long DEFAULT_TIME_TO_LIVE = 600L; 
	/**
	 * Valor default do tempo que uma chave pode permancer no cache apos sua criacao ou desde a sua ultima atualizacao no cache ou desde seu ultimo acesso.
	 */
	public static Long DEFAULT_IDLE_TIME = 600L;
	/**
	 * Valor default do tempo para que uma chave possa ser automaticamente atualizada no cache desde a sua criacao ou do seu mais recente acesso no cache. 
	 */
	public static Long DEFAULT_REFRESH_TIMEOUT = 100L;

}
