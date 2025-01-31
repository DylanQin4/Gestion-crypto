<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plateforme de Cryptomonnaie</title>
   <link rel="stylesheet" href="jsp/css/fonds.css">
</head>
<body>
    <header>
        <nav>
            <div class="site-title">
                <h1>Fonds</h1>
            </div>
        </nav>
    </header>

    <main>
        <section id="portefeuille">
            <h2>Portefeuille</h2>

            <c:if test="${not empty message}">
                <p style="color: green;">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>

            <div id="solde">
                <h3>Solde total : <span id="montant_solde">${solde}</span> BTC</h3>
            </div>

            <div id="operations">
                <button id="depot_btn" onclick="afficherFormulaire('depot')">Dépôt</button>
                <button id="retrait_btn" onclick="afficherFormulaire('retrait')">Retrait</button>

                <div id="formulaire_depot" class="formulaire">
                    <h3>Dépôt</h3>
                    <form action="/depot" method="post">
                        <input type="hidden" name="walletId" value="1"> <%-- ID du portefeuille --%>
                        <input type="number" id="montant_depot" name="montant" placeholder="Montant">
                        <button type="submit">Déposer</button>
                    </form>
                </div>

                <div id="formulaire_retrait" class="formulaire">
                    <h3>Retrait</h3>
                    <form action="/retrait" method="post">
                        <input type="hidden" name="walletId" value="1"> <%-- ID du portefeuille --%>
                        <input type="number" id="montant_retrait" name="montant" placeholder="Montant">
                        <button type="submit">Retirer</button>
                    </form>
                </div>
            </div>

            <div id="historique-transactions">
                <table>
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Montant</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${transactions}" var="transaction">
                            <tr>
                                <td>${transaction.transactionDate}</td>
                                <td>${transaction.transactionType}</td>
                                <td>${transaction.amount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </section>
    </main>

    <footer>
        <p>&copy; 2023 Ma Plateforme de Cryptomonnaie</p>
    </footer>

    <script>
        function afficherFormulaire(type) {
            document.querySelectorAll('.formulaire').forEach(form => form.classList.remove('active'));
            document.getElementById(`formulaire_${type}`).classList.add('active');
        }
    </script>
</body>
</html>