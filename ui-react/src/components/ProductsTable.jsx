export default function ProductsTable({items}){
  return (
    <div className="card">
      <h3 className="font-semibold mb-2">Top 5 Products</h3>
      <table className="table">
        <thead className="thead">
          <tr>
            <th className="py-2">Name</th>
            <th className="py-2">Price</th>
          </tr>
        </thead>
        <tbody>
          {(items||[]).map((p, i)=> (
            <tr key={p.id}
                className="tr hover:bg-slate-50/70 dark:hover:bg-slate-800/50">
              <td className="py-2">{p.name}</td>
              <td className="py-2">
                {new Intl.NumberFormat("sv-SE",{style:"currency",currency:"SEK"}).format(p.price||0)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
