import React from 'react';

// Custom Aesthetic Readrepo Logo Icon (Tailwind CSS)
export const ReadrepoIcon = ({ size = 36, className = '' }) => (
  <svg
    width={size}
    height={size}
    viewBox="0 0 40 40"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
    className={`shrink-0 ${className}`}
  >
    <defs>
      <linearGradient id="readrepo-grad" x1="0%" y1="0%" x2="100%" y2="100%">
        <stop offset="0%" stopColor="#6366F1" />
        <stop offset="50%" stopColor="#8B5CF6" />
        <stop offset="100%" stopColor="#EC4899" />
      </linearGradient>
    </defs>
    
    {/* Soft Glow Background */}
    <rect width="40" height="40" rx="10" fill="url(#readrepo-grad)" fillOpacity="0.15" />

    {/* Book Base / Repo Spine */}
    <path
      d="M12 28V14C12 12.8954 12.8954 12 14 12H20V28H14C12.8954 28 12 27.1046 12 26Z"
      stroke="url(#readrepo-grad)"
      strokeWidth="2.5"
      strokeLinecap="round"
      strokeLinejoin="round"
    />
    <path
      d="M28 28V14C28 12.8954 27.1046 12 26 12H20V28H26C27.1046 28 28 27.1046 28 26Z"
      stroke="url(#readrepo-grad)"
      strokeWidth="2.5"
      strokeLinecap="round"
      strokeLinejoin="round"
    />

    {/* Code Brackets */}
    <path
      d="M15.5 17L14 18.5L15.5 20"
      stroke="url(#readrepo-grad)"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    />
    <path
      d="M24.5 17L26 18.5L24.5 20"
      stroke="url(#readrepo-grad)"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    />
  </svg>
);

// Full Brand Logo Component with Tailwind Typography
export default function ReadrepoLogo({ iconSize = 36 }) {
  return (
    <div className="inline-flex items-center gap-2.5 font-sans select-none cursor-pointer group">
      <ReadrepoIcon size={iconSize} className="transition-transform duration-200 group-hover:scale-105" />
      <span className="text-2xl font-bold tracking-tight text-slate-900 dark:text-white">
        Read
        <span className="bg-linear-to-r from-indigo-500 via-purple-500 to-pink-500 bg-clip-text text-transparent">
          repo
        </span>
      </span>
    </div>
  );
}