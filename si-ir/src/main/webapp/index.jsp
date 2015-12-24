<html>
<body>
<h2>Hello World!</h2>
<form method="post" name="adddoc" action="addoc">
	<label for="url">URL:</label>
	<input type="text" name="url"/>
	
	<label for="caption">Caption:</label>
	<input type="text" name="caption"/>
	
	<label for="context">Context:</label>
	<input type="text" name="context"/>
	
	<input type="submit" name="save" value="Add document"/>
</form>

<form method="post" name="fetchpage" action="fetchpage">
	<label for="url">URL: </label>
	<input type="text" name="url"/>
	
	<label for="storage">Storage folder: </label>
	<input type="text" name="storage"/>
	
	<label for="maxpages">Max pages to fetch:</label>
	<input type="text" name="maxpages"/>
	
	<label for="maxdepth">Max depth:</label>
	<input type="text" name="maxdepth"/>
	
	<input type="submit" name="fetch" value="Fetch page"/>
</form>

<form method="post" name="query" action="query">
	<label for="query">Query string: </label>
	<input type="text" name="query"/>
	
	<input type="submit" name="query" value="Search"/>
</form>
</body>
</html>
