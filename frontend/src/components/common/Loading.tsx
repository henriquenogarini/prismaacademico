interface LoadingProps {
  size?: 'sm' | 'md' | 'lg';
  message?: string;
  fullPage?: boolean;
}

const sizeConfig = {
  sm: { spinner: 'w-5 h-5', text: 'text-xs' },
  md: { spinner: 'w-8 h-8', text: 'text-sm' },
  lg: { spinner: 'w-12 h-12', text: 'text-base' },
};

export default function Loading({
  size = 'md',
  message = 'Carregando...',
  fullPage = false,
}: LoadingProps) {
  const { spinner, text } = sizeConfig[size];

  const content = (
    <div className="flex flex-col items-center gap-3">
      <svg
        className={`animate-spin ${spinner} text-prismaBlue-700`}
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <circle
          className="opacity-25"
          cx="12"
          cy="12"
          r="10"
          stroke="currentColor"
          strokeWidth="4"
        />
        <path
          className="opacity-75"
          fill="currentColor"
          d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"
        />
      </svg>
      {message && (
        <p className={`${text} text-prismaGray-500 font-medium`}>{message}</p>
      )}
    </div>
  );

  if (fullPage) {
    return (
      <div className="fixed inset-0 flex items-center justify-center bg-white/80 backdrop-blur-sm z-50">
        {content}
      </div>
    );
  }

  return (
    <div className="flex items-center justify-center py-16">{content}</div>
  );
}
