import { useEffect } from 'react'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './PremiumLoader.module.css'
import { useTranslation } from 'react-i18next'

interface Props {
    dots: string
    setDots: (updateFn: (prevDots: string) => string) => void
}

const PremiumLoader = ({ dots, setDots }: Props) => {
    const { t } = useTranslation()

    useEffect(() => {
        const interval = setInterval(() => {
            setDots((prevDots) => {
                if (prevDots.length >= 6) {
                    return ''
                }
                return prevDots + ' .'
            })
        }, 1000)

        return () => clearInterval(interval)
    }, [setDots])

    return (
        <div className={styles.loader}>
            <TextRegular text={`${t('premium.payment-check-loader')} ${dots}`} />
        </div>
    )
}

export default PremiumLoader