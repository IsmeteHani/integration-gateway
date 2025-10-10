import React from "react";
import MiniTrend from "./MiniTrend";

export default function StatsCard({stats}){
  if(!stats) return null;
  return (
    <div className="card">
      <h3 className="font-semibold mb-2">Orders</h3>
      <div className="text-slate-700 dark:text-slate-300 space-y-1">
        <div className="flex justify-between">
          <span>Today</span><b>{stats.today}</b>
        </div>
        <div className="flex justify-between">
          <span>Last 7 days</span><b>{stats.last7Days}</b>
        </div>
        <div className="flex justify-between">
          <span>Revenue</span>
          <b>{new Intl.NumberFormat("sv-SE",{style:"currency",currency:"SEK"}).format(stats.revenueSEK||0)}</b>
        </div>
      </div>
      <div className="mt-3">
        <MiniTrend total7d={stats.last7Days||0} />
      </div>
    </div>
  );
}
