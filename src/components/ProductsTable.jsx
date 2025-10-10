export default function ProductsTable({items}){
  return (
    <table className="w-full text-sm">
      <thead>
        <tr className="text-left text-gray-500">
          <th className="py-2">Name</th>
          <th className="py-2">Price</th>
        </tr>
      </thead>
      <tbody>
        {(items||[]).map(p=> (
          <tr key={p.id} className="border-t">
            <td className="py-2">{p.name}</td>
            <td className="py-2">{new Intl.NumberFormat("sv-SE",{style:"currency",currency:"SEK"}).format(p.price||0)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
