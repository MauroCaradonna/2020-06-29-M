package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private Map<Integer, Director> directorIdMap;
	private Map<Integer, Actor> actorIdMap;
	private Graph<Director, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.directorIdMap = new HashMap<Integer, Director>();
		this.dao.listAllDirectors(directorIdMap);
		this.actorIdMap = new HashMap<Integer, Actor>();
		this.dao.listAllActors(actorIdMap);
	}

	public Graph<Director, DefaultWeightedEdge> creaGrafo(int anno){
		this.grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.listAllDirectorsByYear(anno, directorIdMap));
		List<DirettoreAttore> direttoreAttoreList = this.dao.listAllDirectorsActors(anno, directorIdMap, actorIdMap);
		Map<DirettoreDirettore, Set<Actor>> attoriInComune = new HashMap<DirettoreDirettore, Set<Actor>>();
 		for(DirettoreAttore da1 : direttoreAttoreList) {
			for(DirettoreAttore da2 : direttoreAttoreList) {
				if(da1.getAttore().equals(da2.getAttore()) && da1.getDirettore().getId()<da2.getDirettore().getId()) {
					DirettoreDirettore dd = new DirettoreDirettore(da1.getDirettore(), da2.getDirettore());
					if(!attoriInComune.containsKey(dd))
						attoriInComune.put(dd, new HashSet<Actor>());
					attoriInComune.get(dd).add(da1.getAttore());
				}
			}
		}
		for(DirettoreDirettore dd : attoriInComune.keySet())
			Graphs.addEdge(this.grafo, dd.getDirettore1(), dd.getDirettore2(), attoriInComune.get(dd).size());
		return grafo;
	}
	
	public List<DirettorePeso> getAdiacenti(Director regista){
		List<DirettorePeso> adiacenti = new ArrayList<DirettorePeso>();
		for(Director d : Graphs.neighborListOf(grafo, regista)) {
			adiacenti.add(new DirettorePeso(d, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(d, regista))));
		}
		Collections.sort(adiacenti);
		return adiacenti;
	}
}
