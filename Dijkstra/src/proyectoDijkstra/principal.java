/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoDijkstra;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 *
 * @author Angel Uraga
 */

class Edge {

    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Node {

    int value, weight;

    Node(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.value + " (" + this.weight + ")";
    }
}

class Graph {

    List<List<Node>> adjList = new ArrayList<>();

    public Graph(List<Edge> edges) {
        int n = 0;
        for (Edge e : edges) {
            n = Integer.max(n, Integer.max(e.src, e.dest));
        }
        for (int i = 0; i <= n; i++) {
            adjList.add(i, new ArrayList<>());
        }
        for (Edge e : edges) {
            adjList.get(e.src).add(new Node(e.dest, e.weight));
        }
    }

    public String printGraph(Graph graph) {
        int src = 0;
        int n = graph.adjList.size();
        String texto = "";

        while (src < n) {
            for (Node edge : graph.adjList.get(src)) {
                texto += src + " ——> " + edge + "  ";
            }
            System.out.println();
            texto += "\n";
            src++;
        }
        return texto;
    }
}

public class principal extends javax.swing.JFrame {

    long matrizA[][];
    List<Edge> edges = new ArrayList<>();
    String coorVer[];

    /**
     * Creates new form principal
     */
    public principal() {
        initComponents();
    }
    
     public String algoritmo(long[][] matriz) {
        int vertices = matriz.length;
        long matrizA[][] = matriz;
        long peso[][] = matriz;
        String camino[][] = new String[vertices][vertices];
        String caminoA[][] = new String[vertices][vertices];
        String caminoR = "", mensaje = "", caminocorto = "";
        int i, j, k;
        float tem1, tem2, tem3, tem4, min;

        for (i = 0; i < vertices; i++) {
            for (j = 0; j < vertices; j++) {
                camino[i][j] = "";
                caminoA[i][j] = "";
            }
        }

        for (k = 0; k < vertices; k++) {
            for (i = 0; i < vertices; i++) {
                for (j = 0; j < vertices; j++) {
                    tem1 = matrizA[i][j];
                    tem2 = matrizA[i][k];
                    tem3 = matrizA[k][j];
                    tem4 = tem2 + tem3;
                    
                    min = Math.min(tem1, tem4);
                    
                    if(tem1 != tem4){
                        if(min == tem4){
                            caminoR = "";
                            caminoA[i][j] = k + "";
                            camino[i][j] = caminosR(i,k,caminoA,caminoR) + (k+1);
                        }
                    }
                    matrizA[i][j] = (long) min;
                }
            }
        }
        for ( i = 0; i < vertices; i++) {
            for ( j = 0; j < vertices; j++) {
                mensaje = mensaje + "["+matrizA[i][j]+"]";
            }
            mensaje = mensaje + "\n";
        }
        
        for ( i = 0; i < vertices; i++) {
            for ( j = 0; j < vertices; j++) {
                if(matrizA[i][j] != 1000000000){
                    if(i != j){
                        if(camino[i][j].equals("")){
                            caminocorto += "Ruta ("+(i+1)+" ——> "+(j+1)+") Seguir los vertices: ("+(i+1)+", "+(j+1)+").  Total de peso: "+matrizA[i][j]+"\n";
                            
                        }else{
                            caminocorto += "Ruta ("+(i+1)+" ——> "+(j+1)+") Seguir los vertices: ("+(i+1)+", "+camino[i][j]+", "+(j+1)+"). Total de peso: "+matrizA[i][j]+"\n";
                        }
                    }
                }
            } 
        }
        return "\nCaminos mas cortos entre los vertices del grafo: \n"+caminocorto;
    }
     
     public String caminosR(int i, int k, String[][] caminoA, String caminoR){
        if(caminoA[i][k].equals("")){
            return "";
        }else{
            caminoR += caminosR(i, Integer.parseInt(caminoA[i][k].toString()), caminoA, caminoR) + (Integer.parseInt(caminoA[i][k].toString())+1)+", ";
            return caminoR;
        }
    }
     
     public void dibujarVertice(Graphics g, int numV){
         coorVer = new String [numV];
         int contador = 1, x = 10;
         for (int i = 0; i < numV; i++) {
             if(contador == 1){
                 g.setColor(Color.GREEN);
                 g.fillOval(x, 100, 30, 30);
                 g.setColor(Color.BLACK);
                 g.drawString(""+(i+1), x+12, 118);
                 coorVer[i] = (x+12)+"-"+118;
                 contador++;
                 x+=40;
             }else if(contador == 2){
                 g.setColor(Color.CYAN);
                 g.fillOval(x, 50, 30, 30);
                 g.setColor(Color.BLACK);
                 g.drawString(""+(i+1), x+12, 68);
                 coorVer[i] = (x+12)+"-"+68;
                 contador++;
             }else{
                 g.setColor(Color.PINK);
                 g.fillOval(x, 150, 30, 30);
                 g.setColor(Color.BLACK);
                 g.drawString(""+(i+1), x+12, 168);
                 coorVer[i] = (x+12)+"-"+168;
                 contador = 1;
                 x+=40;
             }
         }
     }
     
     public void dibujarArista(Graphics g, int inicio, int fin, int peso){
         String coor1[] = coorVer[inicio].split("-");
         String coor2[] = coorVer[fin].split("-");
         int x1 = Integer.parseInt(coor1[0]);
         int y1 = Integer.parseInt(coor1[1]);
         int x2 = Integer.parseInt(coor2[0]);
         int y2 = Integer.parseInt(coor2[1]);
         g.setColor(Color.BLACK);
         g.drawLine(x1, y1, x2, y2);
         g.setColor(Color.DARK_GRAY);
         g.drawString(""+peso, ((x1+x2)/2), ((y1+y2)/2));
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        matriz = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        relacion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        numVertices = new javax.swing.JTextField();
        crearArista = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vertice2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        vertice1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        pesoArista = new javax.swing.JTextField();
        crearVertice = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        caminosGrafos = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        algoritmo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lienzo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("DotumChe", 1, 24)); // NOI18N
        jLabel1.setText("Algoritmo Dijkstra");

        jLabel2.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        jLabel2.setText("Caminos mas Cortos");

        jLabel3.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        jLabel3.setText("Relación del Grafo");

        matriz.setColumns(20);
        matriz.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        matriz.setRows(5);
        jScrollPane1.setViewportView(matriz);

        relacion.setColumns(20);
        relacion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        relacion.setRows(5);
        jScrollPane2.setViewportView(relacion);

        jLabel4.setText("Ingrese el numero de vertices");

        crearArista.setText("Crear Arista");
        crearArista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearAristaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Creación de arista");

        jLabel6.setText("Vertice");

        jLabel7.setText("a Vertice");

        jLabel8.setText("con un Peso");

        crearVertice.setText("Crear");
        crearVertice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearVerticeActionPerformed(evt);
            }
        });

        caminosGrafos.setColumns(20);
        caminosGrafos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        caminosGrafos.setRows(5);
        jScrollPane3.setViewportView(caminosGrafos);

        jLabel9.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        jLabel9.setText("Matriz de Adyacencia");

        algoritmo.setText("Iniciar Algoritmo");
        algoritmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algoritmoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        jLabel10.setText("Grafo Dirigido");

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
        );
        lienzoLayout.setVerticalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(121, 121, 121))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lienzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(239, 239, 239)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numVertices, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(crearVertice))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vertice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vertice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pesoArista, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(125, 125, 125)
                                        .addComponent(crearArista)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(algoritmo)
                                .addGap(161, 161, 161)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(109, 109, 109))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(numVertices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(crearVertice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(vertice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(vertice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(pesoArista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(crearArista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(algoritmo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lienzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearAristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearAristaActionPerformed
        // TODO add your handling code here:
        try {
            int peso = Integer.parseInt(pesoArista.getText());
            int v1 = Integer.parseInt(vertice1.getSelectedItem().toString());
            int v2 = Integer.parseInt(vertice2.getSelectedItem().toString());
            if(v1 != v2){
                Edge elemento = new Edge(v1, v2, peso);
            edges.add(elemento);
            int p1 = v1 - 1;
            int p2 = v2 -1;
            matrizA[p1][p2] = peso;
            Graphics g = lienzo.getGraphics();
             dibujarArista(g, p1, p2, peso);
            JOptionPane.showMessageDialog(this, "Arista creada: ("+v1+")——"+peso+"——>("+v2+")");
            }else{
                JOptionPane.showMessageDialog(this, "No Ingrese los mismos vertices");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numerico");
        }


    }//GEN-LAST:event_crearAristaActionPerformed

    private void crearVerticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearVerticeActionPerformed
        // TODO add your handling code here:
        try {
            matriz.setText("");
            relacion.setText("");
            caminosGrafos.setText("");
            edges.clear();
            
            int tam = Integer.parseInt(numVertices.getText());
            matrizA = new long[tam][tam];
            for (int i = 0; i < matrizA.length; i++) {
                for (int j = 0; j < matrizA[i].length; j++) {
                    if (i == j) {
                        matrizA[i][j] = 0;
                    } else {
                        matrizA[i][j] = 999999999;
                    }
                }
            }
            vertice1.removeAllItems();
            vertice2.removeAllItems();
            for (int i = 0; i < tam; i++) {
                vertice1.addItem("" + (i + 1));
                vertice2.addItem("" + (i + 1));
            }
            Graphics g = lienzo.getGraphics();
            dibujarVertice(g, tam);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numerico");
        }
    }//GEN-LAST:event_crearVerticeActionPerformed

    private void algoritmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algoritmoActionPerformed
        matriz.setText("");
        if (!edges.isEmpty()) {
            
            for (int i = 0; i < matrizA.length; i++) {
                for (int j = 0; j < matrizA[i].length; j++) {
                    if (matrizA[i][j] == 999999999) {
                        matriz.setText(matriz.getText() + "[∞]");
                    } else {
                        matriz.setText(matriz.getText() + "[" + matrizA[i][j] + "]");
                    }
                }
                matriz.setText(matriz.getText() + "\n");
            }
            String text1 = algoritmo(matrizA);
            caminosGrafos.setText(text1);
            Graph ob = new Graph(edges);
            String text2 = ob.printGraph(ob);
            relacion.setText(text2);
        }else{
            JOptionPane.showMessageDialog(this, "Ingrese un grafo");
        }

    }//GEN-LAST:event_algoritmoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton algoritmo;
    private javax.swing.JTextArea caminosGrafos;
    private javax.swing.JButton crearArista;
    private javax.swing.JButton crearVertice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel lienzo;
    private javax.swing.JTextArea matriz;
    private javax.swing.JTextField numVertices;
    private javax.swing.JTextField pesoArista;
    private javax.swing.JTextArea relacion;
    private javax.swing.JComboBox<String> vertice1;
    private javax.swing.JComboBox<String> vertice2;
    // End of variables declaration//GEN-END:variables
}
