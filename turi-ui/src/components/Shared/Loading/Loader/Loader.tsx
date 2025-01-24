import { ReactNode } from 'react'
import { useStates } from '../../../../hooks/shared/useStates.ts'
import Spinner from '../Spinner'
import styles from './Loader.module.css'

const Loader = ({ children }: { children: ReactNode }) => {
    const { isLoading } = useStates()

    if (isLoading) {
        return (
            <div className={styles.loader}>
                <Spinner />
            </div>
        )
    }

    return <>{children}</>
}

export default Loader
