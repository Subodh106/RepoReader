"use client";

import type { ComponentType } from "react";
import { RequireAuth } from "@/providers/require-auth";
import { AppShell } from "@/components/layout/app-shell";
import RepoDashboard from "@/components/dashboard/page";

const DashboardComponent = RepoDashboard as unknown as ComponentType;

export default function DashboardPage() {
  return (
    <RequireAuth>
      <AppShell hideHeader>
        <DashboardComponent />
      </AppShell>
    </RequireAuth>
  );
}