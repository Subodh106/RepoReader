import { cn } from "@/lib/utils";
import { ReadrepoIcon } from "../icons/ReadRepo";
import { ReactNode } from "react";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useCurrentUser, useLogout } from "@/hooks/use-auth";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarInset,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarProvider,
  SidebarTrigger,
} from "../ui/sidebar";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuItem,
} from "../ui/dropdown-menu";
import { ModeToggle } from "../web/mode-toggler";
import { LayoutDashboard, FolderKanban, Sparkles, Settings, LogOut, Link as LinkIcon } from "lucide-react";
import { Button } from "../ui/button";

const dashboardNavGroups = [
  {
    label: "Workspace",
    items: [
      { title: "Dashboard", href: "/dashboard", exact: true, icon: LayoutDashboard },
      { title: "Projects", href: "/dashboard/projects", icon: FolderKanban },
      { title: "Experiments", href: "/dashboard/experiments", icon: Sparkles },
    ],
  },
];

function isDashboardNavActive(pathname: string | null, href: string, exact = false) {
  if (!pathname) return false;
  if (exact) return pathname === href;
  return pathname === href || pathname.startsWith(`${href}/`);
}

export function BranchMark({ className }: { className?: string }) {
  return (
    <div className={cn("flex items-center gap-2.5 font-semibold tracking-tight", className)}>
      <ReadrepoIcon className="size-8 rounded-[10px]" />
      <span className="font-heading text-[1.05rem]">ReadRepo</span>
    </div>
  );
}

export function AppShell({
  children,
  title,
  description,
  actions,
  hideHeader = false,
}: {
  children: ReactNode;
  title?: string;
  description?: string;
  actions?: ReactNode;
  hideHeader?: boolean;
}) {
  const pathname = usePathname();
  const router = useRouter();
  const { data: user } = useCurrentUser();
  const logout = useLogout();

  const initials = (user?.displayName ?? "DP").slice(0, 2).toUpperCase();

  return (
    <SidebarProvider>
      <Sidebar variant="inset" collapsible="icon">
        <SidebarHeader>
          <SidebarMenu>
            <SidebarMenuItem>
              <SidebarMenuButton
                size="lg"
                render={<Link href="/dashboard" />}
                tooltip="DevPilot"
              >
                <div className="flex size-8 items-center justify-center rounded-[10px] bg-primary/10 text-primary">
                  <ReadrepoIcon className="size-5" />
                </div>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-semibold">DevPilot</span>
                  <span className="truncate text-xs text-muted-foreground">
                    Chat with your code
                  </span>
                </div>
              </SidebarMenuButton>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarHeader>

        <SidebarContent>
          {dashboardNavGroups.map((group) => (
            <SidebarGroup key={group.label}>
              <SidebarGroupLabel>{group.label}</SidebarGroupLabel>
              <SidebarGroupContent>
                <SidebarMenu>
                  {group.items.map((item) => (
                    <SidebarMenuItem key={item.href}>
                      <SidebarMenuButton
                        isActive={isDashboardNavActive(pathname, item.href, item.exact)}
                        tooltip={item.title}
                        render={<Link href={item.href} />}
                      >
                        <item.icon />
                        <span>{item.title}</span>
                      </SidebarMenuButton>
                    </SidebarMenuItem>
                  ))}
                </SidebarMenu>
              </SidebarGroupContent>
            </SidebarGroup>
          ))}
        </SidebarContent>

        <SidebarFooter>
          <SidebarMenu>
            <SidebarMenuItem>
              <DropdownMenu>
                <DropdownMenuTrigger
                  render={
                    <SidebarMenuButton size="lg" className="data-popup-open:bg-sidebar-accent" />
                  }
                >
                  <div className="flex size-8 items-center justify-center rounded-lg bg-muted text-xs font-medium text-sidebar-foreground">
                    {initials}
                  </div>
                  <div className="grid flex-1 text-left text-sm leading-tight">
                    <span className="truncate font-medium">{user?.displayName ?? "Guest"}</span>
                    <span className="truncate text-xs text-muted-foreground">
                      @{user?.githubUsername ?? "unknown"}
                    </span>
                  </div>
                </DropdownMenuTrigger>
                <DropdownMenuContent
                  className="min-w-56 rounded-lg"
                  side="top"
                  align="start"
                  sideOffset={8}
                >
                  <DropdownMenuGroup>
                    <DropdownMenuLabel className="font-normal">
                      <div className="flex flex-col gap-1">
                        <span className="text-sm font-medium">{user?.displayName ?? "Guest"}</span>
                        <span className="text-xs text-muted-foreground">Connected via GitHub</span>
                      </div>
                    </DropdownMenuLabel>
                  </DropdownMenuGroup>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => router.push("/dashboard/settings")}>
                    <Settings className="mr-2 size-4" />
                    Settings
                  </DropdownMenuItem>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => logout.mutate()} disabled={logout.isPending}>
                    <LogOut className="mr-2 size-4" />
                    Log out
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarFooter>
      </Sidebar>

      <SidebarInset>
        {!hideHeader && (
          <header className="sticky top-0 z-20 flex h-14 shrink-0 items-center gap-2 border-b bg-background/80 px-4 backdrop-blur">
            <SidebarTrigger className="-ml-1" />
            <div className="mr-2 h-4 w-px bg-border" />
            <div className="flex min-w-0 flex-1 items-center justify-between gap-3">
              <div className="min-w-0">
                {title && <h1 className="truncate font-heading text-sm font-medium">{title}</h1>}
                {description && (
                  <p className="truncate text-xs text-muted-foreground">{description}</p>
                )}
              </div>
              <div className="flex items-center gap-2">
                {actions}
                <ModeToggle />
              </div>
            </div>
          </header>
        )}
        <div className="flex flex-1 flex-col">{children}</div>
      </SidebarInset>
    </SidebarProvider>
  );
}

export function GhostButtonLink({
  href,
  children,
  className,
}: {
  href: string;
  children: React.ReactNode;
  className?: string;
}) {
  return (
    <Button variant="ghost" size="sm" className={className} render={<Link href={href} />}>
      {children}
    </Button>
  );
}