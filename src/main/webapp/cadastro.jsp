<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
</head>
<body>
<h1>Formulário de Cadastro</h1>
<form action="Cadastrar" method="post">
    Nome: <input type="text" name="nome" required><br>
    E-mail: <input type="email" name="email" required><br>
    Endereço: <input type="text" name="endereco" required><br>
    Data de Nascimento: <input type="date" name="data_Nasc" required><br>
    Telefone: <input type="number" name="telefone" required><br>
    Senha: <input type="password" name="senha" required><br>
    <input type="submit" value="Cadastrar">
</form>
</body>
</html>
