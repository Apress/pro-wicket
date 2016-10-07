<table border="1">
<tr>
	<th>Title</th>
	<th>Author</th>
	<th>Publisher</th>
	<th>Price</th>
</tr>
<#list bookList as book>
<tr>
	<td>${book.title}</td>
	<td>${book.author}</td>
	<td>${book.publisher}</td>
	<td>${book.price}</td>
</tr>
</#list>
</table>