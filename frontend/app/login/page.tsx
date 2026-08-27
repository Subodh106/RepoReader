"use client"

import Loginloading from "@/components/web/Login-loading";
import { useSearchParams } from "next/navigation"
import { useRouter } from "next/router";
import { Suspense } from "react";
import LoginContent from "@/components/web/Login-content";

export default function LoginPage(){
return(
  <>
    <Suspense>
      <LoginContent/>
    </Suspense>
  </>
)
}