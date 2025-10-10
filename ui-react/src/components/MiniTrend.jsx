import React from "react";
import { ResponsiveContainer, LineChart, Line, Tooltip } from "recharts";

function buildSeries(total7d = 0) {
  // krijo 7 pika që totalizojnë afërsisht last7Days (me pak variacion)
  const avg = Math.max(1, Math.round(total7d / 7));
  return Array.from({ length: 7 }, (_, i) => {
    const jitter = Math.round(avg * (0.85 + Math.random() * 0.3));
    return { d: `D${i + 1}`, v: jitter };
  });
}

export default function MiniTrend({ total7d }) {
  const data = buildSeries(total7d);
  return (
    <div className="h-24">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={data} margin={{ top: 8, right: 8, bottom: 0, left: 0 }}>
          <Tooltip
            cursor={false}
            content={({ active, payload }) =>
              active && payload?.length ? (
                <div className="rounded-md bg-white/95 px-2 py-1 text-xs shadow ring-1 ring-slate-200">
                  Orders: <b>{payload[0].value}</b>
                </div>
              ) : null
            }
          />
          <Line type="monotone" dataKey="v" strokeWidth={2} dot={false} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
