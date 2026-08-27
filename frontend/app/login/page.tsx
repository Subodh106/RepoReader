"use client"

import Loginloading from "@/components/web/Login-loading";
import { useSearchParams } from "next/navigation"
import { useRouter } from "next/router";

const LoginPage = () => {
    const Params = useSearchParams();
    const router = useRouter();
    const error = Params.get("errors");
    const next = Params.get("next") || "/dashboard";

    const user = null;
    const isLoading = false;

    if(isLoading || !user){
        return <Loginloading/>
    }
    return (
    <>
    </>
  )
}

export default LoginPage