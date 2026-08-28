"use client"

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