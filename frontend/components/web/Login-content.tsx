"use client"
import Loginloading from "@/components/web/Login-loading";
import { useSearchParams } from "next/navigation"
import { useRouter } from "next/router";
import { Suspense } from "react";
import Navbar from "./Navbar";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../ui/card";
import { Alert, AlertDescription, AlertTitle } from "../ui/alert";
import { AlertCircle, Link } from "lucide-react";
import { cn } from "@/lib/utils";
import { buttonVariants } from "../ui/button";
import { GitBranch } from "lucide-react"
import { getGithubLoginUrl } from "@/lib/api";
import GitHubIconGrid from "../icons/Github-icon";
import { FaGithub } from "react-icons/fa";
import { ReadrepoIcon } from "../icons/ReadRepo";

const LoginContent = () => {
    const Params = useSearchParams();
    // const router = useRouter();
    const error = Params.get("errors");
    const next = Params.get("next") || "/dashboard";

    const user = "user";
    const isLoading = false;

    if(isLoading || !user){
        return <Loginloading/>
    }
    return (
    <>
    <Navbar/>
    <main className="relative z-10 flex flex-1 items-center justify-center px-4 py-10">
        <Card className="w-full max-w-sm border-border/70 bg-card/90 shadow-lg shadow-foreground/5 backdrop-blur-xl">
            <CardHeader className="space-y-4 text-center">
                <div className="mx-auto flex size-12 items-center justify-center rounded-2xl bg-foreground text-background">
                    <FaGithub size={30}/>
                </div>
            <div className="space-y-1">
                <CardTitle className="text-xl">sign in</CardTitle>
                <CardDescription>Connect Github to chat with your repositories</CardDescription>
            </div>
            </CardHeader>
        <CardContent className="space-y-4">
        {error && (
            <Alert variant="destructive" >
            <AlertCircle/>
            <AlertTitle>Sign-in failed</AlertTitle>
            <AlertDescription>Please try again</AlertDescription>
            </Alert>
        )}
        <a
            href={getGithubLoginUrl()}
            className={cn(buttonVariants({size:"lg"}),"inline-flex w-full items-center justify-center gap-2 bg-foreground text-background hover:bg-foreground/90"
        )}
        >
           <FaGithub size={20}/>
            Continue with Github
        </a>
        </CardContent>
        </Card>
    </main>
    </>
  )
}

export default LoginContent;
