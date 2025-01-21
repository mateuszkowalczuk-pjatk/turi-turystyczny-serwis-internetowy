import styles from './ReservationProgressBar.module.css'

const ReservationProgressBar = ({ step }: { step: number }) => {
    return (
        <div className={styles.banner}>
            <div className={`${styles.circle} ${step >= 1 ? styles.filled : ''}`} />
            <div className={`${styles.line} ${step > 1 ? styles.filled : ''}`} />
            <div className={`${styles.circle} ${step >= 2 ? styles.filled : ''}`} />
            <div className={`${styles.line} ${step > 2 ? styles.filled : ''}`} />
            <div className={`${styles.circle} ${step >= 3 ? styles.filled : ''}`} />
        </div>
    )
}

export default ReservationProgressBar
