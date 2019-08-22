package by.bsuir.server.start;

import by.bsuir.client.entity.ExpertsDecision;
import by.bsuir.client.entity.ServerDecision;
import by.bsuir.server.exception.ConnectionException;
import by.bsuir.server.panel.ServerPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private static ServerPanel panel;
    private static final int PORT = 1020;
    private ServerSocket serverSocket;
    private static int clientId = 100;
    public static int countOfClients = 0;

    private Server() throws ConnectException {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            LOGGER.fatal("Сервер не открылся!");
            throw new ConnectException("Сервер не открылся!");
        }
    }

    private static void openPanel() {
        panel = new ServerPanel("Сервер");
        panel.setVisible(true);
        panel.setResizable(false);
        panel.setLocationRelativeTo(null);
        panel.addMessage("Ожидается соединение ...\n");
    }

    private void handleConnection() throws ConnectException {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                countOfClients++;
                panel.addMessage("\nСоединение с клиентом № " + ++clientId + " установлено.\n" +
                        "Количество подключённых клиентов:" + countOfClients + "\n");
                new ConnectionHandler(clientSocket, clientId, panel);
            } catch (IOException e) {
                panel.addMessage(" Ошибка: " + e.toString());
                throw new ConnectException("Клиент не смог подключиться!" + clientId);
            }
        }
    }

    public static void main(String[] args) throws ConnectException {
        Server server = new Server();
        openPanel();
        server.handleConnection();
    }
}


    class ConnectionHandler implements Runnable {

        private static final Logger LOGGER = LogManager.getLogger(ConnectionHandler.class);
        private Socket clientSocket;
        private int clientId;
        private ServerPanel panel;
        private int MATRIX_SIZE;
        private static final int AMOUNT_EXPERT = 3;

        /**
         * Initialize clientSocket, clientId and panel.
         * Create thread and start it.
         *
         * @param clientSocket he socket of client which work in this thread
         * @param clientId     the id of client which work in this thread
         * @param panel        the panel which shows actions which does server
         */
        ConnectionHandler(Socket clientSocket, int clientId, ServerPanel panel) {
            this.clientSocket = clientSocket;
            this.clientId = clientId;
            this.panel = panel;

            Thread t = new Thread(this);
            t.start();
        }


        @Override
        public void run() {
            try {
                boolean isGood = true;
                InputStream IS = clientSocket.getInputStream();
                OutputStream OS = clientSocket.getOutputStream();
                ObjectInputStream ois;
                ObjectOutputStream oos;
                while (isGood) {
                    // Read a message sent by client application
                    ois = new ObjectInputStream(IS);
                    if (ois.readInt() == 1) {
                        ois = new ObjectInputStream(IS);
                        MATRIX_SIZE = ois.readInt();
                        ois = new ObjectInputStream(IS);
                        ExpertsDecision request = (ExpertsDecision) ois.readObject();
                        panel.addMessage("Получен запрос: " + request + "\n");
                        LOGGER.info("Server have request from client № " + clientId + " : " + request);

                        // Send a response information to the client application
                        ServerDecision response1 = solveTaskPart1(request);
                        oos = new ObjectOutputStream(OS);
                        oos.writeObject(response1);
                        panel.addMessage("Первая часть ответа отправлена\n");
                        oos.flush();

                        int response2 = solveTaskPart2(response1);
                        oos = new ObjectOutputStream(OS);
                        oos.writeInt(response2);
                        panel.addMessage("Вторая часть ответа отправлена\n");
                        oos.flush();

                        ois = new ObjectInputStream(IS);
                        if (ois.readInt() == 0) {
                            Server.countOfClients--;
                            panel.addMessage("Соединение с клиентом № " + clientId + " закрыто по его инициативе.");
                            isGood = false;
                        }
                    } else {
                        panel.addMessage("Получен запрос, но он не распознан. Ответ не поступит.\n");
                        LOGGER.warn("Получен запрос, но он не распознан. Ответ не поступит.");
                    }
                }
                IS.close();
                OS.close();
            } catch (IOException | ClassNotFoundException e) {
                try {
                    throw new ConnectionException("Finish connection!" + e);
                } catch (ConnectionException ex) {
                    LOGGER.warn("Finish connection!" + e.getMessage());
                }
            }
        }
        //end run()

        private ServerDecision solveTaskPart1(ExpertsDecision request) {
            List<Integer> list = request.getOpinions();
            int[][] matrix = new int[MATRIX_SIZE][AMOUNT_EXPERT];
            int n = 0;
            for (int j = 0; j < AMOUNT_EXPERT; j++) {
                for (int i = 0; i < MATRIX_SIZE; i++) {
                    matrix[i][j] = list.get(n);
                    n++;
                }
            }
            List<Integer> padding = new ArrayList();
            for (int value = 1; value <= MATRIX_SIZE; value++) {
                padding.add(value);
            }

            List<List<Integer>> hierarchy = new ArrayList<>();
            for (int indexValue = 0; indexValue < MATRIX_SIZE; indexValue++) {
                List<Integer> indexList = new ArrayList<>();
                int expert_an;
                for (int j = 0; j < AMOUNT_EXPERT; j++) {
                    for (int i = 0; i < MATRIX_SIZE; i++) {
                        if (matrix[i][j] == padding.get(indexValue)) {
                            expert_an = i;
                            indexList.add(expert_an);
                        }
                    }
                }
                hierarchy.add(indexList);
            }
            LOGGER.info("Position of variants: " + hierarchy);

            Map<String, Integer> firstBetterSecond = new HashMap<>();
            int ab = 0;
            for (int i = 0; i < hierarchy.size(); i++) {
                for (int j = i + 1; j < hierarchy.size(); j++) {
                    for (int position = 0; position < AMOUNT_EXPERT; position++) {
                        if (hierarchy.get(i).get(position) < hierarchy.get(j).get(position)) {
                            ab++;
                        }
                    }
                    firstBetterSecond.put(i + "better" + j, ab);
                    firstBetterSecond.put(j + "better" + i, AMOUNT_EXPERT - ab);
                    ab = 0;
                }
            }
            LOGGER.info(firstBetterSecond.entrySet());
            return new ServerDecision(firstBetterSecond);
        }

        private int solveTaskPart2(ServerDecision response1) {
            Map<String, Integer> map = response1.getFirstBetterSecond();
            ArrayList<Integer> matrixList = new ArrayList<>();
            switch (MATRIX_SIZE) {
                case 2:
                    matrixList.add(0);
                    matrixList.add(map.get("0better1"));

                    matrixList.add(map.get("1better0"));
                    matrixList.add(0);
                    break;
                case 3:
                    matrixList.add(0);
                    matrixList.add(map.get("0better1"));
                    matrixList.add(map.get("0better2"));

                    matrixList.add(map.get("1better0"));
                    matrixList.add(0);
                    matrixList.add(map.get("1better2"));

                    matrixList.add(map.get("2better0"));
                    matrixList.add(map.get("2better1"));
                    matrixList.add(0);
                    break;
                case 4:
                    matrixList.add(0);
                    matrixList.add(map.get("0better1"));
                    matrixList.add(map.get("0better2"));
                    matrixList.add(map.get("0better3"));

                    matrixList.add(map.get("1better0"));
                    matrixList.add(0);
                    matrixList.add(map.get("1better2"));
                    matrixList.add(map.get("1better3"));

                    matrixList.add(map.get("2better0"));
                    matrixList.add(map.get("2better1"));
                    matrixList.add(0);
                    matrixList.add(map.get("2better3"));

                    matrixList.add(map.get("3better0"));
                    matrixList.add(map.get("3better1"));
                    matrixList.add(map.get("3better2"));
                    matrixList.add(0);
                    break;
                case 5:
                    matrixList.add(0);
                    matrixList.add(map.get("0better1"));
                    matrixList.add(map.get("0better2"));
                    matrixList.add(map.get("0better3"));
                    matrixList.add(map.get("0better4"));

                    matrixList.add(map.get("1better0"));
                    matrixList.add(0);
                    matrixList.add(map.get("1better2"));
                    matrixList.add(map.get("1better3"));
                    matrixList.add(map.get("1better4"));

                    matrixList.add(map.get("2better0"));
                    matrixList.add(map.get("2better1"));
                    matrixList.add(0);
                    matrixList.add(map.get("2better3"));
                    matrixList.add(map.get("2better4"));

                    matrixList.add(map.get("3better0"));
                    matrixList.add(map.get("3better1"));
                    matrixList.add(map.get("3better2"));
                    matrixList.add(0);
                    matrixList.add(map.get("3better4"));

                    matrixList.add(map.get("4better0"));
                    matrixList.add(map.get("4better1"));
                    matrixList.add(map.get("4better2"));
                    matrixList.add(map.get("4better3"));
                    matrixList.add(0);
                    break;
                default:
                    break;
            }

            int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
            int m = 0;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    matrix[i][j] = matrixList.get(m);
                    m++;
                }
            }
            int quantity = 0;
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    if (matrix[i][j] >= matrix[j][i] && i != j) {
                        quantity++;
                    }
                    if (j == MATRIX_SIZE - 1) {
                        if (quantity == MATRIX_SIZE - 1) {
                            return ++i;
                        } else {
                            quantity = 0;
                        }
                    }
                }
            }
            return 0;
        }


}

