import { cn } from "@/lib/utils";
import { ReadrepoIcon } from "../icons/ReadRepo";

export function BranchMark({className}:{className?:string}){
return (
    <div className={cn("flex items-center gap-2.5 font-semibold tracking-tight" , className)}>
        <ReadrepoIcon className="size-8 rounded-[10px]"></ReadrepoIcon>
        <span className="font-heading text-[1.05rem]">ReadRepo</span>
    </div>
)
}