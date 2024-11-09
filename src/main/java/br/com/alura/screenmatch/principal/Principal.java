package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=b42055f2";



    public void exibeMenu(){
        System.out.println("Digite o nome da Série que deseja assistir: ");
        var tituloDaserie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO +tituloDaserie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json,DadosSerie.class);
        System.out.println(dados);


		List<DadosTemporadas> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++){
		    json= consumo.obterDados(ENDERECO +tituloDaserie.replace(" ", "+") +"&season=" + i + API_KEY);
            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);

			temporadas.add(dadosTemporadas);
		}
		temporadas.forEach(System.out::println);
//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//
//
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


    }
}
