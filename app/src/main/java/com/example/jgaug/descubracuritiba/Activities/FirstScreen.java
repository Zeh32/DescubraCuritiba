package com.example.jgaug.descubracuritiba.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jgaug.descubracuritiba.Helpers.DailyItineraryList;
import com.example.jgaug.descubracuritiba.Helpers.Place;
import com.example.jgaug.descubracuritiba.Helpers.PlaceGroup;
import com.example.jgaug.descubracuritiba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class FirstScreen extends AppCompatActivity {
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //Set to fullscreen
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
//        getPlaces();
//        addPlaces();

        setContentView( R.layout.activity_first_screen );
    }

    public void btnCreateItinerary( View view ) {
        Intent intent = new Intent( this, CreateItinerary.class );
        startActivity( intent );
    }

    public void btnRetrieveLastSavedItinerary( View view ) {
        SharedPreferences settings = getSharedPreferences( "mySharedPreferences", MODE_PRIVATE );
        String itineraryJson = settings.getString( "itineraryJson", "fail" );

        DailyItineraryList itinerary = new Gson( ).fromJson( itineraryJson, DailyItineraryList.class );

        if( itinerary == null ) {
            Toast.makeText( this, "Não há nenhum itinerário salvo", Toast.LENGTH_SHORT ).show( );
        } else {
            String dialogMessage = getDialogMessage( itinerary );

            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder
                .setTitle( "Restaurar itinerário" )
                .setMessage( dialogMessage )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener( ) {
                    public void onClick( DialogInterface dialog, int id ) {
                        Intent intent = new Intent( FirstScreen.this, Itinerary.class );
                        intent.putExtra( "itinerary", itinerary );
                        startActivity( intent );
                    }
                } )
                .setNegativeButton( "Não", new DialogInterface.OnClickListener( ) {
                    public void onClick( DialogInterface dialog, int id ) {
                        //Do nothing
                    }
                } );

            AlertDialog dialog = builder.create( );
            dialog.show( );
        }
    }

    public void btnPlacesToVisit( View view ) {
        Toast.makeText( this, "Não implementado", Toast.LENGTH_SHORT ).show( );
    }

    private String getDialogMessage( DailyItineraryList itinerary ) {
        Calendar firstPlaceStartTime = itinerary.getFirstPlaceStartTime( );
        String date = firstPlaceStartTime.get( Calendar.DAY_OF_MONTH ) + " de " + firstPlaceStartTime.getDisplayName( Calendar.MONTH, Calendar.LONG, Locale.getDefault( ) ) + " de " + firstPlaceStartTime.get( Calendar.YEAR ) + ", às " + String.format( "%02d", firstPlaceStartTime.get( Calendar.HOUR_OF_DAY ) ) + ":" + String.format( "%02d", firstPlaceStartTime.get( Calendar.MINUTE ) );

        return "O último itinerário salvo teve inicío no dia " + date + ", com extensão de " + itinerary.getItinerary( ).size( ) + " dia(s). Deseja restaurá-lo?";
    }

    private void getPlaces( ) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener( new ValueEventListener( ) {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                ArrayList< Place > children = dataSnapshot.child( "places" ).getValue( new GenericTypeIndicator< ArrayList< Place > >( ) {
                } );
                addDistances(children);
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                System.out.println( "The read failed: " + databaseError.getCode( ) );
            }
        } );
    }

    private void addPlaces( ) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );
        DatabaseReference postsRef = ref.child( "places" );

        ArrayList< Place > places = new ArrayList<>( );
        places.add( new Place( 0,"Jardim Botânico", "Jardim botanico.jpg", -25.4431219, -49.2449701, true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "O Jardim Botânico de Curitiba foi inaugurado em 1991, com uma área de 245 mil m². Seus jardins geométricos e a estufa de três abóbadas tornaram-se um dos principais cartões postais de Curitiba" ) );
        places.add( new Place( 1,"Bosque Alemão", "bosque alemao.jpg", -25.404949, -49.2869558, true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "Situado em uma área de fundo de vale com 38.000m2 no Jardim Schaffer, local onde no final do século passado a família que deu nome ao bairro era responsável por uma leiteria famosa na região, este bosque conta com equipamentos relacionados à cultura germânica, sendo assim uma homenagem do Prefeito Rafael Greca e da cidade de Curitiba à etnia que aqui se estabeleceu no século 19, a partir de 1833." ) );
        places.add( new Place( 2,"Parque Tanguá", "parque tangua.jpg", -25.3779871, -49.2839598, true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "Inaugurado em 1996, o Parque Tanguá surpreende pela sua beleza. Envolve uma área de 235 mil m², lugar de um antigo complexo de pedreiras desativadas. O Parque Tanguá preserva áreas verdes próximas à nascente do Rio Barigui, com araucárias. Possui uma cascata, dois lagos e um túnel artificial que pode ser visitado de barco ou à pé. O conjunto do parque inclui, também, um mirante, ciclovia, pista de Cooper e lanchonete." ) );
        places.add( new Place( 3,"Parque Barigui", "parque barigui.jpg", -25.4201877, -49.3060934, true, 4, 150, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "\"O nome Barigui tem origem indígena e significa \"rio do fruto espinhoso\", em alusão às pinhas das araucárias nativas, ainda remanescentes. O lugar, uma antiga \"sesmaria\" pertencente a Mateus MartinsLeme, foi transformado em parque em 1972 pelo então prefeito Jaime Lerner. Por sua localização, próximo ao centro da cidade, e sua infraestrutura, o Barigui é o parque mais freqüentado de Curitiba.\"" ) );
        places.add( new Place( 4,"Bosque João Paulo II", "bosque joao paulo.jpg", -25.4094955, -49.2692527, true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "O Bosque João Paulo II, inaugurado em dezembro de 1980, não só eternizou a passagem do Papa por Curitiba em junho de 1980 quando ele visitou a casa típica polonesa montada durante a solenidade no Estádio Couto Pereira, como presenteou a cidade com uma linda homenagem à colônia polonesa." ) );
        places.add( new Place( 5,"Parque Tingui", "parque tingui.JPG", -25.3923778, -49.3050592, true, 4, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "O Parque Tingui foi inaugurado em 1994 com 380 mil m² de área, às margens do rio Barigui. Possui lagos, pontes de madeira cobertas, parque infantil, ciclovia e bastante área verde. Uma ótima opção de lazer em Curitiba." ) );
        places.add( new Place( 6,"Parque São Lourenço", "parque sao loureco.jpg", -25.3870153, -49.2673593, true, 3, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "O Parque São Lourenço foi Inaugurado em 1972, com 204 mil m² de área. Sua criação ocorreu após uma enchente do rio Belém, em 1970, que provocou o rompimento da represa de São Lourenço, paralisando um curtume e a fabrica de cola, que funcionavam no local. O Parque surgiu com as abras de contenção de cheias e de recuperação da área." ) );
        places.add( new Place( 7,"Praça do Japão", "praca do japao.jpg", -25.4455213, -49.2874269, true, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "Numa área bem arborizada de 14 mil m², no bairro de Água Verde, está a Praça do Japão. Uma homenagem à imigração japonesa em Curitiba. Seu projeto foi iniciado em 1958 e a Praça concluída em 1962. Uma reforma, em 1993, incluiu o Portal Japonês e o Memorial da Imigração Japonesa." ) );
        places.add( new Place( 8,"Zoológico Municipal de Curitiba", "zoologico municipal.jpg", -25.5595508, -49.2310989, true, 3, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "O Zoológico de Curitiba está situado no Parque Municipal do Iguaçu, ocupando uma área de 589 mil metros quadrados e que atualmente, está entre os cinco zoológicos mais conceituados do Brasil. Recebe cerca de 650 mil visitantes por ano e está aberto ao público de terça a sexta das 09 às 17 horas e sábados, domingo e feriados das 10h às 16 horas." ) );
        places.add( new Place( 9,"Praça da Espanha", "praca da espanha.jpg", -25.4350475, -49.2867557, true, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "Ponto de encontro com fonte perto da qual crianças brincam e famílias passeiam, entorno com sorvetes e pipoca." ) );
        places.add( new Place( 10,"Bosque Reinhard Maack", "bosque reinhard maack.jpg", -25.4890593, -49.2601908, true, 1, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.PARKS ) ), "Criado em 1989, com 78 mil m² de área, o Bosque Reinhard Maack é uma opção de lazer especial, principalmente para crianças. Inclui uma trilha de aventuras para recreação infantil, onde 15 brinquedos oferecem desafios e obstáculos em níveis variados." ) );
        places.add( new Place( 11,"Passeio Público", "passeio publico.jpg", -25.4253578, -49.2674283, true, 4, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "O Passeio Público é o parque mais central e o primeiro de Curitiba. Inaugurado em 1886 com cerca de 70 mil m² de mata natural, nas margens do rio Belém. Na época sua iluminação era feita por lampiões alimentados por azeite de peixe." ) );

        places.add( new Place( 12,"Praça Osório", "praca osorio.jpg", -25.4329247, -49.2758622, true, 3, 60, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "Nasceu em 1874 e recebeu, em 1878, o nome de Largo Oceano Pacífico. É Praça General Osório desde 1879. Teve coreto em 1914, construído pelo prefeito Cândido Ferreira de Abreu e demolido no início dos anos 50. Seu relógio, restaurado em 1993, relembra o primeiro lá instalado, e marca a hora oficial da cidade. Tem fonte luminosa e equipamentos de lazer para a garotada." ) );
        places.add( new Place( 13,"Praça Santos Andrade", "praca santos andrade.jpg", -25.428763, -49.2665674, true, 2, 30, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "Uma praça cercada de tradição cultural, no Centro de Curitiba. De um lado, a Universidade Federal do Paraná, do outro o Teatro Guaíra, um dos mais importantes do Brasil. A Praça Santos Andrade abriga árvores antigas, vários bustos de personalidades históricas e uma fonte no centro." ) );
        places.add( new Place( 14,"Ópera de Arame", "opera de arame.jpg", -25.384578, -49.2761655, true, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "A Ópera de Arame foi construída em estrutura tubular e teto de policarbonato transparente. O projeto é do arquiteto Domingos Bongestabs, professor do departamento de Arquitetura e Urbanismo da UFPR, o mesmo autor do projeto da Unilivre. Tem capacidade para 2.400 espectadores e um palco de 400m² destinado a apresentações artísticas e culturais." ) );
        places.add( new Place( 15,"Largo da Ordem", "largo da ordem.jpg", -25.4278063, -49.2722547, true, 5, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "O Largo da Ordem é o coração do Centro Histórico de Curitiba e onde se encontra a Igreja da Ordem Terceira de São Francisco das Chagas, a mais antiga de Curitiba. Nos séculos 18, 19 e boa parte do século 20, o Largo era uma área de intenso comércio." ) );
        places.add( new Place( 16,"Estádio Joaquim Américo Guimarães", "estadio joaquim americo.jpg", -25.4482116, -49.2769866, false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "Conhecido como Arena da Baixada, o espaço foi o primeiro palco do futebol brasileiro a adotar o naming rights com o título de Kyocera Arena entre 2005 e 1º de abril de 2008 e com a escolha de Curitiba para ser uma das sedes da Copa do Mundo de 2014, a Arena, entre 2012 e 2014, foi reformada, com a ampliação de capacidade de modo a atender os padrões exigidos pela FIFA, passando a ter 42.370 lugares (capacidade de operação conforme CNEF/CBF 2014 é idêntico a capacidade oficial)." ) );
        places.add( new Place( 17,"Estádio Major Antônio Couto Pereira", "estadio major antonio.jpg", -25.421124, -49.259533, false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "O Estádio Major Antônio Couto Pereira, mais conhecido por Couto Pereira, é um estádio de futebol do estado do Paraná e pertence ao Coritiba Foot Ball Club. Está localizado no bairro Alto da Glória em Curitiba. Seus torcedores o chamam carinhosamente de Couto ou Alto da Glória. Inaugurado em 15 de novembro de 1932, em sua primeira partida de futebol, o Coritiba venceu o America-RJ por 4 a 2. Atualmente o estádio conta com capacidade para 40.310 pessoas." ) );
        places.add( new Place( 18,"Estádio Vila Capanema", "estadio durival britto e silva.jpg", -25.4394883, -49.2558236, false, 2, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "O Estádio Durival Britto e Silva, também conhecido como Vila Capanema, é um estádio de futebol brasileiro localizado em Curitiba e inaugurado em 1947." ) );
        places.add( new Place( 19,"Catedral Metropolitana de Curitiba", "catedral metropolitana de curitiba.jpg", -25.4290119, -49.2714689, false, 5, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "A Catedral Basílica Menor Nossa Senhora da Luz ou Catedral Metropolitana de Curitiba, é um templo católico do município de Curitiba, capital do estado brasileiro do Paraná." ) );
        places.add( new Place( 20,"Torre Panorâmica de Curitiba", "torre panoramica de curitiba.jpg", -25.4242345, -49.2944248, false, 3, 60, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "A única torre telefônica no Brasil com um deck observatório de turismo que está aberta para visitação. Com 109,5 metros de altura, foi inaugurada em 1991 e é um dos pontos turísticos mais visitados de Curitiba. O observatório tem um deck de 360º de visão." ) );
        places.add( new Place( 21,"Memorial de Curitiba", "memorial de curitiba.jpg", -25.4280535, -49.2732833, false, 2, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "Inaugurado em 1996, o Memorial da Cidade de Curitiba é um espaço dedicado à memória, às artes e à cultura da cidade. O público pode assistir a apresentações musicais, ver exposições de arte, peças de teatro ou participar de palestras." ) );
        places.add( new Place( 22,"Mesquita Imam Ali ibn Abi Talib", "mesquita ima ali.jpg", -25.4272692, -49.2773773, false, 1, 30, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.LANDMARKS ) ), "Inaugurado em 1972, o local surgiu da necessidade da comunidade árabe na capital paranaense em ter um espaço sagrado para as orações. O período construtivo durou dois anos e teve projeto arquitetônico de Kamal David Curi (cristão de origem árabe). A estrutura conta com uma cúpula central – ladeada por duas torres denominadas minaretes e orientadas em direção à cidade sagrada de Meca. No interior há escritórios; biblioteca; anfiteatro; e decoração produzida através de doações realizadas pela comunidade muçulmana e empresários árabes da região." ) );
        places.add( new Place( 23,"Praça Tiradentes", "praca tiradentes.jpg", -25.4300983, -49.271682, true, 5, 30, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "A Praça Tiradentes é o berço histórico de Curitiba. A cidade nasceu formalmente aqui. Segundo a lenda, o local teria sido escolhido pelo cacique Tindiquera, da tribo Tingui, para a transferência dos primeiros habitantes da região, até então, acampados às margens do rio Atuba, atual Bairro Alto." ) );
        places.add( new Place( 24,"Praça Rui Barbosa", "praca rui barbosa.jpg", -25.436289, -49.2738464, true, 5, 60, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.PARKS ) ), "Era o antigo Largo da Misericórdia. Recebeu sua primeira urbanização em 1913, quando passou a se chamar Praça da República. O nome foi mudado para Praça Rui Barbosa, em 1923, após a morte do jurista baiano. Em 1954, recebeu nova reforma e outras alterações ocorreram posteriormente para abrigar os terminais de ônibus e o Mercado." ) );

        places.add( new Place( 25,"Museu Oscar Niemeyer", "museu oscar niemeyer.JPG", -25.410174, -49.2669547, false, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.MUSEUMS ) ), "O Museu Oscar Niemeyer é um dos maiores complexos de exposição do Brasil, com cerca de 16 mil m² destinados a obras de arte. Conta com diversos ambientes, incluindo um auditório para 400 lugares, café e espaços de lazer." ) );
        places.add( new Place( 26,"Museu Egípcio e Rosacruz", "museu egipcio e rosacruz.jpg", -25.3904785, -49.2257262, false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "O Museu é uma unidade da Universidade Rose-Croix Internacional e está localizado no seu Campus Metropolitano mantido pela Rosacruz. O Museu está dividido em duas seções: Seção Egípcia e Seção Rosacruz. No Museu Egípcio há réplicas fieis de objetos relacionados ao Antigo Egito cujos originais são conservados no acervo de diversos museus europeus, egípcios e norte-americanos. Esses objetos relacionam-se a muitos aspectos da vida daquele povo, assim como à sua organização social, sua religião e sua política." ) );
        places.add( new Place( 27,"Museu Paranaense", "museu paranaense.jpg", -25.427709, -49.276074, false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "Fundado em 1876, o Museu Paranaense foi o primeiro museu do Paraná, idealizado pelo baiano Dr. Murici e pelo paranaense Agostinho Ermelino de Leão (1834-1901). Possui um acervo de cerca de 300 mil peças e documentos. São peças etnográficas de origem indígena de várias partes do Brasil, peças arqueológicas, mapas antigos, peças históricas das antigas capitanias do sul do País e obras de arte." ) );
        places.add( new Place( 28,"Museu do Automóvel de Curitiba", "museu do automovel.jpg", -25.422979, -49.30635, false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "A história do Museu do Automóvel iniciou em 1968, quando um grupo de entusiastas por automóveis antigos, fundou o Clube de Automóveis e Antiguidades Mecânicas do Paraná – CAAMP, com o intuito de congregar os apreciadores destas máquinas antigas incentivando a sua preservação. Após oito anos de atividade, foi fundado em 1976 o Museu do Automóvel que propiciou expor ao público, o acervo dos sócios do CAAMP com mais 150 veículos, sendo estes constantemente alternados nas 70 vagas existentes no Museu que se dividem nas categorias: Vintage, Nacionais e de Corrida, sendo na atualidade, um dos mais expressivos museus do gênero em nosso país." ) );
        places.add( new Place( 29,"Museu Ferroviário", "museu ferroviario.jpg", -25.437499, -49.265796, false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "Instalado na antiga Estação Ferroviária de Curitiba, o Museu Ferroviário expõe peças históricas e suas instalações buscam reproduzir o antigo funcionamento da estação. Inclui parte do acervo da RFFSA, como uma locomotiva do início do século 20 e um vagão dormitório que serviu para hospedar o ex-presidente Getúlio Vargas. Hoje, o museu faz parte das instalações do Shopping Estação." ) );
        places.add( new Place( 30,"Museu do Expedicionário", "museu do expedicionario.jpg", -25.428618, -49.258505, false, 2, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "O Museu do Expedicionário, criado em 1946, ilustra a participação do Brasil na Segunda Guerra Mundial e, em especial, a participação dos soldados paranaenses. Possui farto material histórico, incluindo muitas ilustrações, mapas, livros e documentos da época. Estão expostos vários materiais bélicos e armamentos utilizados na guerra pela Força Expedicionária Brasileira, pela Força Aérea Brasileira e pela Marinha de Guerra do Brasil." ) );
        places.add( new Place( 31,"Museu de Arte Contemporânea do Paraná", "museu da arte contemporanea.jpg", -25.4331338, -49.2728765, false, 1, 90, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.MUSEUMS ) ), "O Museu de Arte Contemporânea do Paraná (MAC/PR) é uma entidade mantida pelo governo do Paraná, que tem em seu acervo pinturas, esculturas, desenhos, gravuras e outros tipos de obras de diversos artistas brasileiros, em especial de artistas paranaenses." ) );

        places.add( new Place( 32,"Mercadoteca", "mercadoteca.jpg", -25.441991, -49.322225, false, 2, 120, new ArrayList< Integer >( Collections.singletonList( PlaceGroup.FOOD ) ), "A Mercadoteca esta localizada no bairro Mossunguê, em poucas palavras é um mini mercado municipal porém mais requintado. Lá pode-se comprar: flores, ingredientes para os mais variados pratos, utensílios de cozinha, e ainda há vários restaurantes. Tem estacionamento próprio, e uma área externa legal para sentar e conversar, além de um espaço com brinquedos para as crianças." ) );
        places.add( new Place( 33,"Mercado Municipal de Curitiba", "mercado municipal.jpg", -25.4343079, -49.2572541, false, 4, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.SHOPPING, PlaceGroup.FOOD ) ), "Fundado em 02 de agosto de 1958, o Mercado Municipal  de Curitiba é o principal e mais tradicional endereço para compras de Curitiba.  Nas bancas de hortigranjeiros,  nas lojas de delicatessens e setor orgânico, o consumidor encontra produtos como: bebidas, queijos e vinhos de diversas procedências, ervas medicinais, temperos e especiarias, iguarias, conservas, pescados, embutidos, carnes exóticas e com cortes especiais. Além também de produtos de decoração, utensílios domésticos, produtos de limpeza, armarinhos, presentes, entre outros." ) );
        places.add( new Place( 34,"Rua XV de Novembro", "rua xv de novembro.jpg", -25.4291112, -49.2663552, true, 5, 120, new ArrayList< Integer >( Arrays.asList( PlaceGroup.LANDMARKS, PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Em 1857, a então Rua das Flores possuía apenas três quadras, entre as atuais ruas Dr. Muricy e Barão do Rio Branco. Era estreita, sem pavimentação e iluminada apenas por dois lampiões, localizados próximos ao Palácio do Governo Provincial. Ali também estavam instalados dois armazéns de secos e molhados, duas lojas de alfaiates, uma casa de bilhar, uma casa que alugava cavalos e, no número 13, a redação do Jornal Dezenove de Dezembro." ) );
        places.add( new Place( 35,"Shopping Estação", "shopping estacao.jpg", -25.437958, -49.2656081, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "O Shopping Estação foi inaugurado em 1997 em prédio anexo ao Museu Ferroviário, que reúne a memória sobre a antiga estação ferroviária de Curitiba, atraindo o público com uma extensa lista de atrações para o curitibano. Localizado em região central e cercado por grandes avenidas, o empreendimento tornou-se referência na cidade." ) );
        places.add( new Place( 36,"Shopping Curitiba", "shopping curitiba.jpg", -25.4409824, -49.2774002, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Inaugurado em 26 de setembro de 1996, localizado no antigo imóvel militar do século XIX, o Shopping Curitiba é um dos shoppings mais queridos pelo público curitibano. Em 2007, um grande processo de revitalização se iniciou para deixá-lo ainda melhor. O Curitiba é o shopping mais prático, rápido e bem organizado da cidade, que oferece tudo o que você precisa com a satisfação de ter feito as escolhas certas em um ambiente agradável, onde você sempre se sente à vontade. Cerca de 9 milhões de pessoas passam pelo Shopping Curitiba no ano, 750 mil ao mês, o equivalente a metade da população de Curitiba! " ) );
        places.add( new Place( 37,"Shopping Palladium", "shopping palladium.jpg", -25.4780084, -49.2909862, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "É o maior shopping de Curitiba e do Sul do Brasil, com suas mais de 350 lojas, sendo 20 lojas-âncora, cerca de 80 quiosques, um charmoso Boulevard com 8 restaurantes - cada um deles com capacidade para acolher 150 pessoas - mais 4 restaurantes e outras 26 opções de fast-food para atender a todos os gostos, numa imensa Praça de Alimentação, com mais de 1.200 lugares sentados." ) );
        places.add( new Place( 38,"Shopping Barigui", "shopping barigui.jpg", -25.434937, -49.316264, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Com mais de 300 lojas nacionais e internacionais, você tem bons motivos para fazer ser incrível o seu Dia das Mães, Dia dos Pais, Dia dos Namorados, ou até aquela data que você adora: o Dia Em Que Eu Me Presenteio." ) );
        places.add( new Place( 39,"Shopping Palladium", " ", 0, 0, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "descrição" ) );
        places.add( new Place( 40,"Shopping Cidade", "shopping cidade.jpg", -25.471794, -49.252598, false, 1, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Em 2003, nascia o Shopping Cidade. A prova de que preços imbatíveis podem conviver com um ambiente superagradável, cheio de opções de lazer, cultura e diversão para toda a família. Venha nos fazer uma visita e conhecer tudo o que preparamos para você. Aqui todo mundo é muito bem-vindo." ) );
        places.add( new Place( 41,"Shopping Total", "shopping total.jpg", -25.4787444, -49.2943671, false, 2, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Localizado no bairro Portão, o Shopping Total Curitiba foi construído em um terreno de 31 mil m² e conta com 77 mil m² de área construída. Famoso na cidade por oferecer um amplo mix de lojas com preço sempre mais barato, o shopping está a 15 minutos do centro de Curitiba com fácil acesso para quem mora nos bairros Cidade Industrial, Novo Mundo, Fazendinha, Sitio Cercado, Água Verde, Capão Raso, Boqueirão e Alto Boqueirão. O Shopping Total Curitiba conta hoje com 4 lojas âncora: Americanas, Havan, Cassol e Casa China, além de cinco salas de cinema com a marca Cinesystem e uma grande praça de alimentação com 16 lojas, entre elas Burger King, Bobs, McDonald’s, Subway, Difrango, Rock Grill e Bier Hoff. Com acesso facilitado a todos os públicos, o shopping conta com forte divulgação na mídia e promoções sazonais." ) );
        places.add( new Place( 42,"Shopping Crystal", "shopping crystal.jpg", -25.4391307, -49.2812202, false, 2, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Reconhecido pela arquitetura moderna e ambiente acolhedor, o Shopping Crystal reúne diversas opções de compras, serviços e gastronomia para atender o público da região com conforto e conveniência." ) );
        places.add( new Place( 43,"Shopping Mueller", "shopping mueller.jpg", -25.4234408, -49.2704616, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Aqui você encontra mais de 200 lojas de marcas de renome nacional e internacional, o moderno complexo de cinemas Cinemark, que conta com salas de exibição em 3D, além de restaurantes, atividades culturais e as melhores experiências." ) );
        places.add( new Place( 44,"Shopping Pátio Batel", "shopping patio batel.jpg", -25.4430477, -49.2909159, false, 3, 150, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Reunimos aqui Compras, Lazer, Entretenimento e Cultura, de um jeito que só Curitiba pode oferecer. Muito verde, luz natural, áreas de circulação amplas e espaços criados para proporcionar encontros com a família, com os amigos e consigo mesmo." ) );
        places.add( new Place( 45,"Shopping Jardim das Américas", "shopping jardim da americas.jpg", -25.4513486, -49.2284538, false, 1, 90, new ArrayList< Integer >( Arrays.asList( PlaceGroup.FOOD, PlaceGroup.SHOPPING ) ), "Localizado no bairro Jardim das Américas, o Shopping tem desenvolvido uma forte relação com a comunidade local, seja por meio dos laços de compras ou primando pelo desenvolvimento social. O resultado disso pode ser observado na fidelização dos clientes e na confiança que as pessoas têm no empreendimento." ) );

        postsRef.setValue( places );
    }

    public void addDistances(ArrayList<Place> places){
        final FirebaseDatabase database = FirebaseDatabase.getInstance( );
        DatabaseReference ref = database.getReference( "" );
        DatabaseReference postsRef = ref.child( "distances" );

        Double[][] distances = new Double[46][46];

        for(int a=0;a<places.size();a++){
            for(int b=0;b<places.size();b++){
                distances[a][b] = distance(places.get(a).getLatitude(),places.get(b).getLatitude(),places.get(a).getLongitude(),places.get(b).getLongitude(),900,900);
            }
        }

        List<Double> distancesList = twoDArrayToList(distances);

        postsRef.setValue(distancesList);
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public <Double> List<Double> twoDArrayToList(Double[][] twoDArray) {
        List<Double> list = new ArrayList<>();
        for (Double[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }
}